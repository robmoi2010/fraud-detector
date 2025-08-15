package com.goglotek.fraud_detector.dataextractionservice.service;

import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.model.FileDto;
import com.goglotek.fraud_detector.dataextractionservice.model.Transaction;

import java.util.List;


public interface DataStoreService {
    public String storeFile(FileDto file) throws GoglotekException;

    public String storeTransactions(List<Transaction> transactions) throws GoglotekException;
}
