package com.goglotek.frauddetector.dataextractionservice.extractors;

import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.schema.Schema;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataExtractor {
    public List<Transaction> extractTransactions(byte[] fileData, Schema schema) throws IOException, GoglotekException;

    Date getFromDate();

    Date getToDate();
}
