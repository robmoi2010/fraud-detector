package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaUser;

public interface MpesaUserService {

	MpesaUser findByEmail(String email);

	MpesaUser create(MpesaUser user);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<MpesaUser> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

	Optional<MpesaUser> findById(Long userId);

	void deleteById(long userId);

}