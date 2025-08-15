package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaRole;

public interface MpesaRoleService {

	List<MpesaRole> createAll(List<MpesaRole> roles);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<MpesaRole> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

}