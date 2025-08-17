package com.goglotek.frauddetector.dataextractionservice.service;

import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;

import java.io.IOException;

public interface DataProcessingService {
    public TransactionsDto extractFilesData(FileDto file) throws GoglotekException, IOException;
}
