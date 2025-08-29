package com.goglotek.frauddetector.datastoreservice.service.impl;

import java.util.*;

import com.goglotek.frauddetector.datastoreservice.dto.DiscrepancyType;
import com.goglotek.frauddetector.datastoreservice.dto.ProcessedTransaction;
import com.goglotek.frauddetector.datastoreservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.*;
import com.goglotek.frauddetector.datastoreservice.repository.ProviderLocalTransactionsRepository;
import com.goglotek.frauddetector.datastoreservice.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;

@Service
public class ProviderLocalTransactionsServiceImpl implements ProviderLocalTransactionsService {
    @Autowired
    private ProviderLocalTransactionsRepository providerLocalTransactionsRepository;

    @Autowired
    private FilesService filesService;

    @Autowired
    private ProviderTransactionsService providerTransactionsService;

    @Autowired
    private LocalTransactionsService localTransactionsService;

    @Autowired
    private ProcessingService processingService;

    private final String FRAUDULENT_MESSAGE = "Fraudulent transaction. Has no record in the provider transactions";
    private final String INVALID_AMT_MESSAGE = "Invalid amount. Amount does not match with the provider transactions";
    private final String INVALID_CLIENT_ID_MESSAGE = "Invalid client id. The transaction belongs to another client account";
    private final String INVALID_TIMESTAMP_MESSAGE = "Invalid timestamp. The transactions timestamp does not match";
    private final String MISSING_MESSAGE = "Missing transaction. The automated system might not have received the transaction. Depending on policy, the transaction might be recreated.";


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

    @Transactional
    @Override
    public void storeProcessedTransactions(ProcessedTransactionDto processedTrans) throws GoglotekException {
        List<ProviderTransactions> providerTransactions = providerTransactionsService.findByFileGlobalId(processedTrans.getFileGlobalId());
        List<LocalTransactions> localTransactions = localTransactionsService.findByTimePeriod(processedTrans.getFrom(), processedTrans.getTo());
        Files file = filesService.getFileByGlobalId(processedTrans.getFileGlobalId());

        //create the processing object
        Processing processing = new Processing();
        processing.setCreatedDate(new Date());
        processing.setFileId(file.getFileId());
        processing.setModifiedDate(new Date());
        processing.setFileId(file.getFileId());
        processing.setUsername("MACHINE");
        processing.setTotalDiscrepancy(processedTrans.getFraudulentTransactions().size() + processedTrans.getMissingTransactions().size() + processedTrans.getInvalidAmountTransactions().size() + processedTrans.getInvalidTimestampTransactions().size() + processedTrans.getInvalidClientAccountTransactions().size());
        processing = processingService.save(processing);

        //update valid transactions
        for (ProviderTransactions pt : providerTransactions) {
            if (isValidTransaction(processedTrans, pt.getTransactionId())) {
                LocalTransactions lt = localTransactions.stream().filter(s -> s.getTransactionId().equals(pt.getTransactionId())).findFirst().get();
                saveTransaction(processing.getProcessId(), pt, lt, true, null);
            }
        }

        //update invalid transactions

        //update fraudulent transactions
        //present in local transactions but absent in provider transactions
        for (ProcessedTransaction p : processedTrans.getFraudulentTransactions()) {
            LocalTransactions lt = localTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            saveTransaction(processing.getProcessId(), null, lt, false, DiscrepancyType.FRAUDULENT);
        }

        //update Missing transactions
        //present in provider transactions but absent in local transactions
        for (ProcessedTransaction p : processedTrans.getMissingTransactions()) {
            ProviderTransactions pt = providerTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            saveTransaction(processing.getProcessId(), pt, null, false, DiscrepancyType.MISSING);
        }

        //update invalid amount transactions
        //genuine transaction but amount might be increased fraudulently
        for (ProcessedTransaction p : processedTrans.getInvalidAmountTransactions()) {
            LocalTransactions lt = localTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            ProviderTransactions pt = providerTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            saveTransaction(processing.getProcessId(), pt, lt, false, DiscrepancyType.INVALID_AMOUNT);
        }

        //update invalid client account transactions
        //genuine transaction but might be redirected to a different client
        for (ProcessedTransaction p : processedTrans.getInvalidClientAccountTransactions()) {
            LocalTransactions lt = localTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            ProviderTransactions pt = providerTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            saveTransaction(processing.getProcessId(), pt, lt, false, DiscrepancyType.INVALID_CLIENT_ACC);
        }

        //update invalid timestamp transactions
        //probably reused transaction
        for (ProcessedTransaction p : processedTrans.getInvalidTimestampTransactions()) {
            LocalTransactions lt = localTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            ProviderTransactions pt = providerTransactions.stream().filter(s -> s.getTransactionId().equals(p.getId())).findFirst().get();
            saveTransaction(processing.getProcessId(), pt, lt, false, DiscrepancyType.INVALID_TIMESTAMP);
        }

        //update file, set processed to true
        //TODO: for paying cients, retry process if an error occurs and transaction are already uploaded to data store
        file.setProcessed(true);
        file.setModifiedDate(new Date());
        filesService.save(file);

    }

    private void saveTransaction(Long processingId, ProviderTransactions pt, LocalTransactions lt, boolean isValidTrans, DiscrepancyType discrepancyType) {
        ProviderLocalTransactions trans = new ProviderLocalTransactions();

        trans.setCreatedDate(new Date());
        trans.setModifiedDate(new Date());
        trans.setProcessingId(processingId);
        trans.setPushedToLocal(false);
        trans.setFlaggedInLocal(false);
        trans.setValidTransaction(isValidTrans);

        if (isValidTrans) {
            trans.setComments("Valid Transaction");
        }

        if (pt != null) {
            trans.setProviderTransactions(pt);
        }
        if (lt != null) {
            trans.setLocalTransactions(lt);
        }

        if (discrepancyType != null) {
            trans.setDiscrepancyType(discrepancyType.value);
            trans.setComments(getCommentBasedOnDiscrepancyType(discrepancyType));
            trans.setAction("For paying client:Notifications can be sent or transaction recreated in the local transaction store");
        }
        providerLocalTransactionsRepository.save(trans);
    }

    private String getCommentBasedOnDiscrepancyType(DiscrepancyType type) {
        switch (type) {
            case FRAUDULENT:
                return FRAUDULENT_MESSAGE;
            case MISSING:
                return MISSING_MESSAGE;
            case INVALID_AMOUNT:
                return INVALID_AMT_MESSAGE;
            case INVALID_CLIENT_ACC:
                return INVALID_CLIENT_ID_MESSAGE;
            case INVALID_TIMESTAMP:
                return INVALID_TIMESTAMP_MESSAGE;
            default:
                return "Invalid transaction";
        }
    }


    private boolean isValidTransaction(ProcessedTransactionDto dto, String id) {
        //check if transaction is in the fraudulent transactions
        boolean present = dto.getFraudulentTransactions().stream().anyMatch(s -> s.getId().equals(id));
        if (present) {
            return false;
        }

        //check if transaction is in the missing transactions
        present = dto.getMissingTransactions().stream().anyMatch(s -> s.getId().equals(id));
        if (present) {
            return false;
        }

        //check if transaction is in the invalid amount transactions
        present = dto.getInvalidAmountTransactions().stream().anyMatch(s -> s.getId().equals(id));
        if (present) {
            return false;
        }

        //check if transaction is in the invalid client account transactions
        present = dto.getInvalidClientAccountTransactions().stream().anyMatch(s -> s.getId().equals(id));
        if (present) {
            return false;
        }

        //check if transaction is in the invalid timestamp transactions
        present = dto.getInvalidTimestampTransactions().stream().anyMatch(s -> s.getId().equals(id));
        if (present) {
            return false;
        }

        //return true if the transaction is not in any of the invalid ones
        return true;

    }

}
