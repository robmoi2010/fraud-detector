package com.goglotek.frauddetector.dataextractionservice.event;

import java.util.ArrayList;
import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;
import com.goglotek.frauddetector.dataextractionservice.service.DataProcessingService;
import com.goglotek.frauddetector.dataextractionservice.service.DataStoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;

@Component
public class EventHandler {
    @Autowired
    private DataProcessingService dataProcessingService;

    @Autowired
    private DataStoreService dataStoreService;

    @Autowired
    private EventDispatcher eventDispatcher;

    @RabbitListener(queues = "${goglotek.ftp.queue}")
    void handleFtpFileDownload(final List<FileDto> files) {
        //TODO: send both file and its transactions data combined
        List<FileDto> newList = new ArrayList<>();
        try {
            for (FileDto file : files) {
                TransactionsDto transactionsDto = dataProcessingService.extractFilesData(file);

                file.setTotalTransactions(transactionsDto.getTransactions().size());
                file.setGroupAccount(transactionsDto.getTransactions().get(0).getGroupAccount());
                file.setFromDate(transactionsDto.getFromDate());
                file.setToDate(transactionsDto.getToDate());

                //send file data
                dataStoreService.storeFile(file);
                newList.add(file);
                //send transaction to datastore service
                dataStoreService.storeTransactions(transactionsDto.getTransactions());
            }

            //send files to be processed by the data processor
            if (newList.isEmpty()) {
                eventDispatcher.send(newList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
