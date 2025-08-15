package com.goglotek.mpesareconciliation.handlerservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.handlerservice.model.MpesaFileDto;

public interface MpesaFilesService {
	void reconcileTransactions(List<MpesaFileDto> files);
}