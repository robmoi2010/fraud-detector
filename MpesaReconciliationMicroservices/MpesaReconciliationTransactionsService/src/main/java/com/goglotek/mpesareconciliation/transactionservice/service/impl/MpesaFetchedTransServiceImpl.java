package com.goglotek.mpesareconciliation.transactionservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFetchedTrans;
import com.goglotek.mpesareconciliation.transactionservice.repository.MpesaFetchedTransRepository;
import com.goglotek.mpesareconciliation.transactionservice.service.MpesaFetchedTransService;

@Service
public class MpesaFetchedTransServiceImpl implements MpesaFetchedTransService {
	@Autowired
	MpesaFetchedTransRepository mpesaFetchedTransRepository;

	@Override
	public List<MpesaFetchedTrans> list() {
		return mpesaFetchedTransRepository.findAll();
	}

	@Override
	public MpesaFetchedTrans save(MpesaFetchedTrans entity) {
		return mpesaFetchedTransRepository.save(entity);
	}
}
