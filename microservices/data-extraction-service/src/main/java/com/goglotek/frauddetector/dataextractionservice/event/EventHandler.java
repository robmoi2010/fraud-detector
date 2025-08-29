package com.goglotek.frauddetector.dataextractionservice.event;

import java.util.ArrayList;
import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;
import com.goglotek.frauddetector.dataextractionservice.extractors.CsvExtractor;
import com.goglotek.frauddetector.dataextractionservice.service.DataProcessingService;
import com.goglotek.frauddetector.dataextractionservice.service.DataStoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;

@Component
public class EventHandler {
    static final Logger logger = LogManager.getLogger(CsvExtractor.class);
    @Autowired
    private DataProcessingService dataProcessingService;

    @Autowired
    private DataStoreService dataStoreService;

    @Autowired
    private EventDispatcher eventDispatcher;

    @RabbitListener(queues = "${goglotek.ftp.queue}")
    void handleFtpFileDownload(final List<FileDto> files) {
        try {
            //TODO: for paying clients, send both file and its transactions data combined
            logger.info("Received " + files.size() + " files for data extraction");
            List<FileDto> newList = new ArrayList<>();
            for (FileDto file : files) {
                try {
                    TransactionsDto transactionsDto = dataProcessingService.extractFilesData(file);

                    file.setTotalTransactions(transactionsDto.getTransactions().size());
                    file.setGroupAccount(transactionsDto.getTransactions().get(0).getGroupAccount());
                    file.setFromDate(transactionsDto.getFromDate());
                    file.setToDate(transactionsDto.getToDate());

                    //send file data
                    String resp = dataStoreService.storeFile(file);

                    //send transaction to datastore service
                    String transResp = dataStoreService.storeTransactions(transactionsDto.getTransactions(), file.getFileId());
                    if (resp.contains(file.getFileName()) && transResp.toLowerCase().contains("success")) {
                        newList.add(file);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            //send files to be processed by the data processor
            if (!newList.isEmpty()) {
                eventDispatcher.send(newList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
