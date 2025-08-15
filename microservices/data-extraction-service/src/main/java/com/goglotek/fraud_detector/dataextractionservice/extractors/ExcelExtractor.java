package com.goglotek.fraud_detector.dataextractionservice.extractors;

import com.goglotek.fraud_detector.dataextractionservice.model.Transaction;
import com.goglotek.fraud_detector.dataextractionservice.schema.Schema;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

//TODO: Implement for paying clients
public class ExcelExtractor implements DataExtractor{
    @Override
    public List<Transaction> extractTransactions(byte[] data, Schema schema) throws IOException {
        return Collections.emptyList();
    }
}
