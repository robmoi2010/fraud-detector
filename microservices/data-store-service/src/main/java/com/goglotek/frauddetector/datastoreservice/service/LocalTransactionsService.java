package com.goglotek.frauddetector.datastoreservice.service;

import java.util.Date;
import java.util.List;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.model.LocalTransactions;

public interface LocalTransactionsService {

    long countAllFilteredPaged(List<FilterModel> filterModel);

    List<LocalTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
                                                 List<FilterModel> filterModel);

    List<LocalTransactions> findByTimePeriod(Date from, Date to);
}