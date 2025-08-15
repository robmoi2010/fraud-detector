package com.goglotek.fraud_detector.dataextractionservice.extractors;

import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.model.Transaction;
import com.goglotek.fraud_detector.dataextractionservice.schema.Schema;

import java.io.IOException;
import java.util.List;

public interface DataExtractor {
    public List<Transaction> extractTransactions(byte[] fileData, Schema schema) throws IOException, GoglotekException;
}
