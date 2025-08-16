package com.goglotek.frauddetector.dataextractionservice.service;

import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.model.FileDto;
import com.goglotek.frauddetector.dataextractionservice.model.Transaction;

import java.io.IOException;
import java.util.List;

public interface DataProcessingService {
    public List<Transaction> extractFilesData(FileDto file) throws GoglotekException, IOException;
}
