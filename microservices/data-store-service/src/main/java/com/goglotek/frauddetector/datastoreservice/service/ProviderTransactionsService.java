package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;

public interface ProviderTransactionsService {

	List<ProviderTransactions> list();

	List<ProviderTransactions> findAllPaged(Integer page, Integer limit, String order, String direction);

	Optional<ProviderTransactions> findById(Long transId);

	List<ProviderTransactions> search(String lowerCase);

	long countAll();

	List<ProviderTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

	long countAllFilteredPaged(List<FilterModel> filterModel);

}