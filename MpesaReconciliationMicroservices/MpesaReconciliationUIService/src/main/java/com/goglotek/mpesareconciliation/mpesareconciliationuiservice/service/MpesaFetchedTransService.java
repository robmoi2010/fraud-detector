package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchedTrans;

public interface MpesaFetchedTransService {

	List<MpesaFetchedTrans> list();

	List<MpesaFetchedTrans> findAllPaged(Integer page, Integer limit, String order, String direction);

	Optional<MpesaFetchedTrans> findById(Long transId);

	List<MpesaFetchedTrans> search(String lowerCase);

	long countAll();

	List<MpesaFetchedTrans> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

	long countAllFilteredPaged(List<FilterModel> filterModel);

}