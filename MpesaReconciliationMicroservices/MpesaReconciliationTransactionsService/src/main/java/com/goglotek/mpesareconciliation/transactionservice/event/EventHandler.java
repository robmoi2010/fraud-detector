package com.goglotek.mpesareconciliation.transactionservice.event;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFileDto;
import com.goglotek.mpesareconciliation.transactionservice.service.MpesaFilesService;

@Component
public class EventHandler {
	@Autowired
	MpesaFilesService mpesaFilesService;

	@Autowired
	EventDispatcher eventDispatcher;

	@RabbitListener(queues = "${goglotek.ftp.queue}")
	void handleFtpFileDownload(final List<MpesaFileDto> event) {
		try {
			List<MpesaFileDto> files = mpesaFilesService.stageTransactions(event);
			if (!files.isEmpty()) {
				eventDispatcher.send(files);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
