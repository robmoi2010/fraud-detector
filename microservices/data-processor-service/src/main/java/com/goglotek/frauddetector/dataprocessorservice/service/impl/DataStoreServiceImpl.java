package com.goglotek.frauddetector.dataprocessorservice.service.impl;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import com.goglotek.frauddetector.dataprocessorservice.rest.RestClient;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataStoreServiceImpl implements DataStoreService {
    @Autowired
    RestClient restClient;

    @Override
    public List<Transaction> getProviderTransactions(FileDto file) {
        return Collections.emptyList();
    }

    @Override
    public List<Transaction> getLocalTransactions(FileDto file) {
        return Collections.emptyList();
    }

    @Override
    public void storeProcessedTransactionData(ProcessedTransactionDto processed) {

    }
}
