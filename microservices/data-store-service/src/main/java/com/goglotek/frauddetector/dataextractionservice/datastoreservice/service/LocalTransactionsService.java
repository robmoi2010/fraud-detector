package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.LocalTransactions;

public interface LocalTransactionsService {

	long countAllFilteredPaged(List<FilterModel> filterModel);

	List<LocalTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
												 List<FilterModel> filterModel);

}