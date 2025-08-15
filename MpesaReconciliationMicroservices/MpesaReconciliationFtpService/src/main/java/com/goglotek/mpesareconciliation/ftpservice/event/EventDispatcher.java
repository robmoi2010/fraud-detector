package com.goglotek.mpesareconciliation.ftpservice.event;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.goglotek.mpesareconciliation.ftpservice.domain.MpesaFileDto;

@Component
public class EventDispatcher {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${goglotek.mpesareconciliation.exchange}")
	private String serviceExchange;
	@Value("${goglotek.ftp.routing_key}")
	private String ftpRoutingKey;

	public void send(final List<MpesaFileDto> ftpFiles) {
		rabbitTemplate.convertAndSend(serviceExchange, ftpRoutingKey, ftpFiles);
	}

}
