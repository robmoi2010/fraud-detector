package com.goglotek.fraud_detector.dataprocessorservice.service;

import java.util.List;

import com.goglotek.fraud_detector.dataprocessorservice.model.MpesaFileDto;

public interface MpesaFilesService {
	void reconcileTransactions(List<MpesaFileDto> files);
}