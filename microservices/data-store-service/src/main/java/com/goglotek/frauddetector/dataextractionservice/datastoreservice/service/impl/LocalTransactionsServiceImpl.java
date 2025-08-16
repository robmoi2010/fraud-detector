package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.LocalTransactions;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository.LocalTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.LocalTransactionsService;

@Service
public class LocalTransactionsServiceImpl implements LocalTransactionsService {
    @Autowired
    private LocalTransactionsRepository localTransactionsRepository;

    private LocalTransactions createFilterObject(List<FilterModel> filterModel) {
        LocalTransactions trans = new LocalTransactions();
        for (FilterModel m : filterModel) {
            String filterColumn = m.getColumnField();
            String value = m.getValue();
            switch (filterColumn.toLowerCase()) {
                case "transactionid":
                    trans.setTransactionId(value);
                    break;
                case "amount":
                    trans.setAmount(Double.parseDouble(value));
                    break;
                case "clientaccount":
                    trans.setClientAccount(value);
                    break;
            }
        }
        return trans;
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
        Example<LocalTransactions> example = Example.of(createFilterObject(filterModel),
                createFilterMatcher(filterModel));
        return localTransactionsRepository.count(example);
    }

    @Override
    public List<LocalTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String
                                                                direction,
                                                        List<FilterModel> filterModel) {
        Example<LocalTransactions> example = Example.of(createFilterObject(filterModel),
                createFilterMatcher(filterModel));
        return localTransactionsRepository
                .findAll(example, PageRequest.of(page, limit,
                        direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
                .getContent();
    }

}
