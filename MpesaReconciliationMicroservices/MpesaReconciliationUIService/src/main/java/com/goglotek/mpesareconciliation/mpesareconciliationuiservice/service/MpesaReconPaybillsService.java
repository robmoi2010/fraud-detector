package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconPaybills;

public interface MpesaReconPaybillsService {
	MpesaReconPaybills findByPaybill(String paybill);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<MpesaReconPaybills> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

	List<MpesaReconPaybills> findAll();
}