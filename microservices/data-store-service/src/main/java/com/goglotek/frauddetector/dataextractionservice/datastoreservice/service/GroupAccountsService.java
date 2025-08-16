package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.GroupAccounts;

public interface GroupAccountsService {
	GroupAccounts findByAccount(String account);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<GroupAccounts> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

	List<GroupAccounts> findAll();
}