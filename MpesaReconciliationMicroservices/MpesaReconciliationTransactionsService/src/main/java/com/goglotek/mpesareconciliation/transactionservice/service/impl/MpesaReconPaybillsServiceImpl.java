package com.goglotek.mpesareconciliation.transactionservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaReconPaybills;
import com.goglotek.mpesareconciliation.transactionservice.repository.MpesaReconPaybillsRepository;
import com.goglotek.mpesareconciliation.transactionservice.service.MpesaReconPaybillsService;

@Service
public class MpesaReconPaybillsServiceImpl implements MpesaReconPaybillsService {
	@Autowired
	MpesaReconPaybillsRepository mpesaReconPaybillsRepository;

	@Override
	public List<MpesaReconPaybills> list() {
		return mpesaReconPaybillsRepository.findAll();
	}

	@Override
	public MpesaReconPaybills findByPaybill(String paybill) {
		return mpesaReconPaybillsRepository.findByPaybill(paybill);
	}
}
