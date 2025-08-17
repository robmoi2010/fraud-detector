package com.goglotek.frauddetector.dataprocessorservice.event;

import java.util.List;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.service.DataProcessingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @Autowired
    private DataProcessingService dataProcessingService;

    @RabbitListener(queues = "${goglotek.frauddetector.queue}")
    void handleTransactionsProcessing(final List<FileDto> event) {
        try {
            dataProcessingService.processData(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
