package com.goglotek.mpesareconciliation.transactionservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFetchedTrans;

public interface MpesaFetchedTransService {

	List<MpesaFetchedTrans> list();

	MpesaFetchedTrans save(MpesaFetchedTrans entity);

}