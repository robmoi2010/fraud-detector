package com.goglotek.frauddetector.dataextractionservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.model.FileDto;
import com.goglotek.frauddetector.dataextractionservice.model.Transaction;
import com.goglotek.frauddetector.dataextractionservice.rest.RestClient;
import com.goglotek.frauddetector.dataextractionservice.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataStoreServiceImpl implements DataStoreService {
    @Autowired
    private RestClient restClient;

    @Autowired
    private Config config;

    @Autowired
    private Cryptography cryptography;

    @Override
    public String storeFile(FileDto file) throws GoglotekException {
        try {
            String unencryptedTxt = new ObjectMapper().writeValueAsString(file);
            //encrypt the data
            byte[] encrypted = cryptography.encrypt(unencryptedTxt.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector());
            return restClient.post(config.getBaseUrl() + "" + config.getPushFilesEndpoint(), new String(encrypted));
        } catch (JsonProcessingException e) {
            throw new GoglotekException(e, "File parse to json failed:" + e.getMessage());
        } catch (Exception e) {
            throw new GoglotekException(e, "Error occurred while sending files to server:" + e.getMessage());
        }
    }

    //TODO: for paying clients, batch transactions if they are more than a certain threshold
    @Override
    public String storeTransactions(List<Transaction> transactions) throws GoglotekException {
        try {
            String unencryptedTxt = new ObjectMapper().writeValueAsString(transactions);
            //encrypt the data
            byte[] encrypted = cryptography.encrypt(unencryptedTxt.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector());
            return restClient.post(config.getBaseUrl() + "" + config.getPushTransactionsEndpoint(), new String(encrypted));
        } catch (JsonProcessingException e) {
            throw new GoglotekException(e, "Transactions parse to json failed:" + e.getMessage());
        } catch (Exception e) {
            throw new GoglotekException(e, "Error occurred while sending files to server:" + e.getMessage());
        }
    }
}
