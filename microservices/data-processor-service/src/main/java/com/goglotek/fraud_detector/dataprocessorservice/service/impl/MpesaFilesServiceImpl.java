package com.goglotek.fraud_detector.dataprocessorservice.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.fraud_detector.dataprocessorservice.model.MpesaFileDto;
import com.goglotek.fraud_detector.dataprocessorservice.repository.MpesaFilesRepository;
import com.goglotek.fraud_detector.dataprocessorservice.service.MpesaFilesService;

@Service
public class MpesaFilesServiceImpl implements MpesaFilesService {
	Logger logger = LogManager.getLogger(MpesaFilesServiceImpl.class);
	@Autowired
	MpesaFilesRepository mpesaFilesRepository;

	@Override
	public void reconcileTransactions(List<MpesaFileDto> files) {
		for (MpesaFileDto file : files) {
			try {
				logger.info("Reconciling transactions for file " + file.getFileName());
				mpesaFilesRepository.reconcileTransactions(file.getFileId());
				logger.info("Successfuly reconciled transactions for file " + file.getFileName());
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

}
