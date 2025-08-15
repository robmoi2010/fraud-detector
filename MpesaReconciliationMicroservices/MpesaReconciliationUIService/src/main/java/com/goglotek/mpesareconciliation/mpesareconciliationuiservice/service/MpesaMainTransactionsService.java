package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaMainTransactions;

public interface MpesaMainTransactionsService {

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<MpesaMainTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

}