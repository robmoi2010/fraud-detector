package com.goglotek.fraud_detector.ftpservice.event;

import java.util.List;

import com.goglotek.fraud_detector.ftpservice.configuration.Config;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.fraud_detector.ftpservice.domain.TransactionsFile;

@Component
public class EventDispatcher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Config config;


    public void send(final List<TransactionsFile> ftpFiles) {
        rabbitTemplate.convertAndSend(config.getServiceExchange(), config.getFtpRoutingKey(), ftpFiles);
    }

}
