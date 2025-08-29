package com.goglotek.frauddetector.dataprocessorservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataprocessorservice.configuration.Config;
import com.goglotek.frauddetector.dataprocessorservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataprocessorservice.dto.*;
import com.goglotek.frauddetector.dataprocessorservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataprocessorservice.rest.RestClient;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

@Service
public class DataStoreServiceImpl implements DataStoreService {
    @Autowired
    RestClient restClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Config config;

    @Autowired
    private Cryptography cryptography;

    @Override
    public List<Transaction> getProviderTransactions(FileDto file) throws Exception {
        //get transactions from data store
        String response = restClient.get(config.getBaseUrl() + config.getGetProviderTransactionsEndpoint() + "/" + file.getFileId());
        mapper.addMixIn(Transaction.class, ProviderTransactionsMixin.class);
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
        mapper.addMixIn(Transaction.class, LocalTransactionsMixin.class);
        return mapper.readValue(response, new TypeReference<List<Transaction>>() {
        });
    }

    @Override
    public void storeProcessedTransactionData(ProcessedTransactionDto processed) throws Exception {
        String payload = new ObjectMapper().writeValueAsString(processed);
        byte[] encrypted = cryptography.encrypt(payload.getBytes(), config.getEncryptionKey(), config.getInitVector());
        String response = restClient.post(config.getBaseUrl() + config.getProcessedTransEndpoint(), Base64.getEncoder().encodeToString(encrypted));
        SuccessResponse resp = mapper.readValue(response, SuccessResponse.class);
        if (!resp.isSuccess()) {
            throw new GoglotekException("Error sending processed data: " + response);
        }
    }
}
