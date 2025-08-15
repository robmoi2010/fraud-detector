package com.goglotek.fraud_detector.dataextractionservice.service;

import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.model.FileDto;
import com.goglotek.fraud_detector.dataextractionservice.model.Transaction;

import java.io.IOException;
import java.util.List;

public interface DataProcessingService {
    public List<Transaction> extractFilesData(FileDto file) throws GoglotekException, IOException;
}
