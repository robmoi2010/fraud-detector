package com.goglotek.mpesareconciliation.handlerservice.event;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.mpesareconciliation.handlerservice.model.MpesaFileDto;
import com.goglotek.mpesareconciliation.handlerservice.service.MpesaFilesService;

@Component
public class EventHandler {
	@Autowired
	MpesaFilesService mpesaFilesService;

	@RabbitListener(queues = "${goglotek.recon.queue}")
	void handleTransactionsReconciliation(final List<MpesaFileDto> event) {
		try {
			mpesaFilesService.reconcileTransactions(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
