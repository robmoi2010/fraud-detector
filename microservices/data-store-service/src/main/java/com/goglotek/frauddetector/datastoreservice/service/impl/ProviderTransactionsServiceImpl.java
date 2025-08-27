package com.goglotek.frauddetector.datastoreservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.datastoreservice.dto.CreateProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.exception.FileNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;
import com.goglotek.frauddetector.datastoreservice.repository.ProviderTransactionsRepository;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.service.ProviderTransactionsService;

@Service
public class ProviderTransactionsServiceImpl implements ProviderTransactionsService {
    @Autowired
    ProviderTransactionsRepository providerTransactionsRepository;

    @Autowired
    FilesService filesService;

    @Override
    public List<ProviderTransactions> list() {
        return providerTransactionsRepository.findAll();
    }

    private ProviderTransactions createFilterObject(List<FilterModel> filterModel) {
        ProviderTransactions providerTransactions = new ProviderTransactions();
        for (FilterModel m : filterModel) {
            String filterColumn = m.getColumnField();
            String value = m.getValue();
            switch (filterColumn.toLowerCase()) {
                case "transactionid":
                    providerTransactions.setTransactionId(value);
                    break;
                case "details":
                    providerTransactions.setDetails(value);
                    break;
                case "amount":
                    providerTransactions.setAmount(Double.parseDouble(value));
                    break;
                case "fileid":
                    providerTransactions.setFileId(Long.parseLong(value));
                    break;

                case "clientaccount":
                    providerTransactions.setClientAccount(value);
                    break;
            }
        }
        return providerTransactions;
    }

    private ExampleMatcher createFilterMatcher(List<FilterModel> filterModel) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        for (FilterModel m : filterModel) {
            switch (m.getOperatorValue().toLowerCase()) {
                case "contains": {
                    matcher = matcher
                            .withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                            .withIgnoreNullValues();
                    break;
                }
                case "equals": {
                    matcher = matcher
                            .withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                            .withIgnoreNullValues();
                    break;
                }
                case "startswith": {
                    matcher = matcher
                            .withMatcher(m.getColumnField(),
                                    ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                            .withIgnoreNullValues();
                    break;
                }
                case "endswith": {
                    matcher = matcher
                            .withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase())
                            .withIgnoreNullValues();
                    break;
                }
                default:
                    matcher = matcher
                            .withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                            .withIgnoreNullValues();
                    break;
            }
        }
        matcher = matcher.withIgnoreNullValues();
        return matcher;
    }

    @Override
    public long countAllFilteredPaged(List<FilterModel> filterModel) {
        Example<ProviderTransactions> example = Example.of(createFilterObject(filterModel),
                createFilterMatcher(filterModel));
        return providerTransactionsRepository.count(example);
    }

    @Transactional
    @Override
    public List<ProviderTransactions> createAll(List<CreateProviderTransactionsDto> transactionsDtoList, Files file) throws GoglotekException {
        List<ProviderTransactions> transactions = new ArrayList<>();
        for (CreateProviderTransactionsDto dto : transactionsDtoList) {
            ProviderTransactions p = new ProviderTransactions();
            p.setClientAccount(dto.getClientAccount());
            p.setAmount(dto.getAmount());
            p.setTransactionId(dto.getId());
            p.setCreatedDate(new Date());
            p.setDetails(dto.getDetails());
            p.setTransactionTime(dto.getTransactionTimestamp());
            p.setFileId(file.getFileId());
            p.setModifiedDate(new Date());
            transactions.add(p);
        }
        List<ProviderTransactions> l = providerTransactionsRepository.saveAll(transactions);
        if (l.size() != transactions.size()) {
            throw new GoglotekException("Error persisting provider transactions. Changes have been rolled back");
        }
        return l;
    }

    @Override
    public List<ProviderTransactions> findByFileGlobalId(String fileGlobalId) throws GoglotekException {
        Files file = filesService.getFileByGlobalId(fileGlobalId);
        ProviderTransactions trans = new ProviderTransactions();
        trans.setFileId(file.getFileId());
        Example<ProviderTransactions> example = Example.of(trans);
        return providerTransactionsRepository.findAll(example);
    }

    @Override
    public List<ProviderTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String
                                                                   direction,
                                                           List<FilterModel> filterModel) {
        Example<ProviderTransactions> example = Example.of(createFilterObject(filterModel),
                createFilterMatcher(filterModel));
        return providerTransactionsRepository
                .findAll(example, PageRequest.of(page, limit,
                        direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
                .getContent();
    }

    @Override
    public List<ProviderTransactions> findAllPaged(Integer page, Integer limit, String order, String direction) {
        return providerTransactionsRepository
                .findAll(PageRequest.of(page, limit,
                        direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
                .getContent();
    }

    @Override
    public Optional<ProviderTransactions> findById(Long transId) {
        return providerTransactionsRepository.findById(transId);
    }

    @Override
    public List<ProviderTransactions> search(String keyword) {
        return providerTransactionsRepository.search(keyword, keyword, keyword);
    }

    @Override
    public long countAll() {
        return providerTransactionsRepository.count();
    }
}
