package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.ProviderLocalTransactions;

public interface ProviderLocalTransactionsService {

	Optional<ProviderLocalTransactions> findById(Long reconId);

	List<ProviderLocalTransactions> findAllPaged(Integer page, Integer limit, String order, String direction);

	List<ProviderLocalTransactions> search(String keywords);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<ProviderLocalTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

}