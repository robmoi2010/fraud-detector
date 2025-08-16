package com.goglotek.frauddetector.dataextractionservice.event;

import java.util.ArrayList;
import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.model.Transaction;
import com.goglotek.frauddetector.dataextractionservice.service.DataProcessingService;
import com.goglotek.frauddetector.dataextractionservice.service.DataStoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.frauddetector.dataextractionservice.model.FileDto;

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
        List<FileDto> newList = new ArrayList<>();
        try {
            for (FileDto file : files) {
                List<Transaction> transactions = dataProcessingService.extractFilesData(file);
                file.setTotalTransactions(transactions.size());
                file.setGroupAccount(transactions.get(0).getGroupAccount());

                //send file data
                dataStoreService.storeFile(file);
                newList.add(file);
                //send transaction to datastore service
                dataStoreService.storeTransactions(transactions);
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
