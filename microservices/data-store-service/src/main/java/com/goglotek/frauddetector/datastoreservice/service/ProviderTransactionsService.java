package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.datastoreservice.dto.CreateProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;
import jakarta.transaction.Transactional;

public interface ProviderTransactionsService {

    List<ProviderTransactions> list();

    List<ProviderTransactions> findAllPaged(Integer page, Integer limit, String order, String direction);

    Optional<ProviderTransactions> findById(Long transId);

    List<ProviderTransactions> search(String lowerCase);

    long countAll();

    List<ProviderTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
                                                    List<FilterModel> filterModel);

    long countAllFilteredPaged(List<FilterModel> filterModel);

    @Transactional
    List<ProviderTransactions> createAll(List<CreateProviderTransactionsDto> transactionsDtoList, Files file) throws GoglotekException;

    public List<ProviderTransactions> findByFileGlobalId(String fileGlobalId) throws GoglotekException;
}