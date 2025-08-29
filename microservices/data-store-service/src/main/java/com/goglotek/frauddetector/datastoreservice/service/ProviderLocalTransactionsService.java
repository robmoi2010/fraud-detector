package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.ProviderLocalTransactions;

public interface ProviderLocalTransactionsService {

	Optional<ProviderLocalTransactions> findById(Long reconId);

	List<ProviderLocalTransactions> findAllPaged(Integer page, Integer limit, String order, String direction);

	List<ProviderLocalTransactions> search(String keywords);

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<ProviderLocalTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel);

    void storeProcessedTransactions(ProcessedTransactionDto processedTrans) throws GoglotekException;
}