package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconTransactions;

public interface MpesaReconTransactionsService {

	Optional<MpesaReconTransactions> findById(Long reconId);

	List<MpesaReconTransactions> findAllPaged(Integer page, Integer limit, String order, String direction);

	List<MpesaReconTransactions> search(String keywords);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<MpesaReconTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

}