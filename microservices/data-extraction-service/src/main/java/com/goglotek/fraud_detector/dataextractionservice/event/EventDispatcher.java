package com.goglotek.fraud_detector.dataextractionservice.event;

import java.util.List;

import com.goglotek.fraud_detector.dataextractionservice.configuration.Config;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.fraud_detector.dataextractionservice.model.FileDto;

@Component
public class EventDispatcher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Config config;


    public void send(final List<FileDto> files) {
        rabbitTemplate.convertAndSend(config.getFilesExchange(), config.getFilesRoutingKey(), files);
    }

}
