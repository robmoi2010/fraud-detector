package com.goglotek.fraud_detector.dataprocessorservice.event;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.fraud_detector.dataprocessorservice.model.MpesaFileDto;
import com.goglotek.fraud_detector.dataprocessorservice.service.MpesaFilesService;

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
