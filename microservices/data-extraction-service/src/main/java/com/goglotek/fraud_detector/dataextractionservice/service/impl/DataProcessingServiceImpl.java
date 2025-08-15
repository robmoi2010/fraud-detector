package com.goglotek.fraud_detector.dataextractionservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.fraud_detector.dataextractionservice.configuration.Config;
import com.goglotek.fraud_detector.dataextractionservice.client.FileClient;
import com.goglotek.fraud_detector.dataextractionservice.cryptography.Cryptography;
import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.extractors.DataExtractor;
import com.goglotek.fraud_detector.dataextractionservice.model.FileDto;
import com.goglotek.fraud_detector.dataextractionservice.model.Transaction;
import com.goglotek.fraud_detector.dataextractionservice.schema.Schema;
import com.goglotek.fraud_detector.dataextractionservice.service.DataProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DataProcessingServiceImpl implements DataProcessingService {
    @Autowired
    private FileClient fileClient;

    @Autowired
    private Config config;

    @Autowired
    private Cryptography cryptography;

    @Autowired
    private DataExtractor dataExtractor;

    private Schema schema;
    private final String SCHEMA_PATH = "Schema.json";

    public DataProcessingServiceImpl() {
        this.schema = getSchema();
    }

    private Schema getSchema() {
        try {
            return new ObjectMapper().readValue(Files.readAllBytes(Paths.get(SCHEMA_PATH)), Schema.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Transaction> extractFilesData(FileDto file) throws GoglotekException, IOException {
        byte[] encryptedFile = fileClient.getFile(file.getAbsolutePath());
        if (encryptedFile == null) {
            throw new GoglotekException("File Not found");
        }
        byte[] decryptedFile = cryptography.decrypt(encryptedFile, config.getEncryptionKey(), config.getEncryptionInitVector());
        return dataExtractor.extractTransactions(decryptedFile, this.schema);
    }

}
