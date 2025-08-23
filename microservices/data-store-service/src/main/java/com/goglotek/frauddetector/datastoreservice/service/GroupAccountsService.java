package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.model.GroupAccounts;

public interface GroupAccountsService {
	GroupAccounts findByAccount(String account);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<GroupAccounts> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

	List<GroupAccounts> findAll();
}