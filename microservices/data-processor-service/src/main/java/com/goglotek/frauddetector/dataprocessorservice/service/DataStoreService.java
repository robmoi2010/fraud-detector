package com.goglotek.frauddetector.dataprocessorservice.service;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;

import java.util.List;

public interface DataStoreService {

    List<Transaction> getProviderTransactions(FileDto file) throws Exception;

    List<Transaction> getLocalTransactions(FileDto file) throws Exception;

    void storeProcessedTransactionData(ProcessedTransactionDto processed);
}
