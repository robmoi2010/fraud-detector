package com.goglotek.frauddetector.dataextractionservice.service;

import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;

import java.util.List;


public interface DataStoreService {
    public String storeFile(FileDto file) throws GoglotekException;

    public String storeTransactions(List<Transaction> transactions) throws GoglotekException;
}
