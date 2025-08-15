package com.goglotek.mpesareconciliation.transactionservice.event;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFileDto;

@Component
public class EventDispatcher {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${goglotek.mpesareconciliation.exchange}")
	private String serviceExchange;

	@Value("${goglotek.recon.routing_key}")
	private String reconRoutingKey;

	public void send(final List<MpesaFileDto> files) {
		rabbitTemplate.convertAndSend(serviceExchange, reconRoutingKey, files);
	}

}
