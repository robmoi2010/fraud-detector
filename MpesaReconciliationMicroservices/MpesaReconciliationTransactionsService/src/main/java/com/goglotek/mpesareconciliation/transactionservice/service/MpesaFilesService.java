package com.goglotek.mpesareconciliation.transactionservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFileDto;
import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFiles;

public interface MpesaFilesService {

	

	MpesaFiles save(MpesaFiles file);

	MpesaFiles findByFileName(String name);

	public List<MpesaFileDto> stageTransactions(List<MpesaFileDto> files);

}