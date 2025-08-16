package com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.LocalTransactions;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.ProviderLocalTransactions;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.ProviderTransactions;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.repository.ProviderLocalTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.ProviderLocalTransactionsService;

@Service
public class ProviderLocalTransactionsServiceImpl implements ProviderLocalTransactionsService {
    @Autowired
    ProviderLocalTransactionsRepository providerLocalTransactionsRepository;

    @Override
    public Optional<ProviderLocalTransactions> findById(Long reconTransId) {
        return providerLocalTransactionsRepository.findById(reconTransId);
    }

    @Override
    public List<ProviderLocalTransactions> findAllPaged(Integer page, Integer limit, String order, String direction) {
        return providerLocalTransactionsRepository
                .findAll(PageRequest.of(page, limit,
                        direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
                .getContent();
    }

    @Override
    public List<ProviderLocalTransactions> search(String keywords) {
        return providerLocalTransactionsRepository.search(keywords, keywords);
    }

    private ProviderLocalTransactions createFilterObject(List<FilterModel> filterModel) {
        ProviderTransactions providerTransactions = new ProviderTransactions();
        LocalTransactions localTransactions = new LocalTransactions();
        ProviderLocalTransactions lpTransactions = new ProviderLocalTransactions();
        Set<Integer> discType = new HashSet<Integer>();
        for (FilterModel m : filterModel) {
            String filterColumn = m.getColumnField();
            String value = m.getValue();
            switch (filterColumn.toLowerCase()) {
                case "transactionid":
                    localTransactions.setTransactionId(value);
                    break;
                case "details":
                    providerTransactions.setDetails(value);
                    break;
                case "amount":
                    localTransactions.setAmount(Double.parseDouble(value));
                    break;
                case "fileid":
                    providerTransactions.setFileId(Long.parseLong(value));
                    break;
                case "account":
                    localTransactions.setClientAccount(value);
                    break;
            }
        }

        return lpTransactions;
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
        Example<ProviderLocalTransactions> example = Example.of(createFilterObject(filterModel),
                createFilterMatcher(filterModel));
        return providerLocalTransactionsRepository.count(example);
    }

    @Override
    public List<ProviderLocalTransactions> findAllFilteredPaged(Integer page, Integer limit, String order,
                                                                String direction, List<FilterModel> filterModel) {
        Example<ProviderLocalTransactions> example = Example.of(createFilterObject(filterModel),
                createFilterMatcher(filterModel));
        return providerLocalTransactionsRepository
                .findAll(example, PageRequest.of(page, limit,
                        direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
                .getContent();
    }

}
