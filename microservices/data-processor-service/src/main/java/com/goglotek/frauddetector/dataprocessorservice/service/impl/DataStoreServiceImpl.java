package com.goglotek.frauddetector.dataprocessorservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataprocessorservice.configuration.Config;
import com.goglotek.frauddetector.dataprocessorservice.dto.*;
import com.goglotek.frauddetector.dataprocessorservice.rest.RestClient;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class DataStoreServiceImpl implements DataStoreService {
    @Autowired
    RestClient restClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Config config;

    @Override
    public List<Transaction> getProviderTransactions(FileDto file) throws Exception {
        //get transactions from data store
        String response = restClient.get(config.getBaseUrl() + config.getGetProviderTransactionsEndpoint() + "/" + file.getFileId());
        return mapper.readValue(response, new TypeReference<List<Transaction>>() {
        });
    }

    @Override
    public List<Transaction> getLocalTransactions(FileDto file) throws Exception {
        //get transactions from data store
        SimpleDateFormat format = new SimpleDateFormat(config.getInternalTimestampFormat());
        String from = format.format(file.getFromDate());
        String to = format.format(file.getToDate());
        String response = restClient.get(config.getBaseUrl() + config.getGetLocalTransactionsEndpoint() + "?from=" + from + "&to=" + to);
        return mapper.readValue(response, new TypeReference<List<Transaction>>() {
        });
    }

    @Override
    public void storeProcessedTransactionData(ProcessedTransactionDto processed) {

    }
}
