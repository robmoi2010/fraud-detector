package com.goglotek.mpesareconciliation.transactionservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.transactionservice.model.MpesaReconPaybills;

public interface MpesaReconPaybillsService {

	List<MpesaReconPaybills> list();

	MpesaReconPaybills findByPaybill(String paybill);

}