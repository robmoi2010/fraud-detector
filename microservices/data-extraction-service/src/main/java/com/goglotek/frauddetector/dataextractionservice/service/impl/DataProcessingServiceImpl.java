package com.goglotek.frauddetector.dataextractionservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.client.FileClient;
import com.goglotek.frauddetector.dataextractionservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataextractionservice.dto.FileType;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.extractors.DataExtractor;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;
import com.goglotek.frauddetector.dataextractionservice.schema.Schema;
import com.goglotek.frauddetector.dataextractionservice.service.DataProcessingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;

@Service
public class DataProcessingServiceImpl implements DataProcessingService {
    static final Logger logger = LogManager.getLogger(DataProcessingServiceImpl.class);
    @Autowired
    private FileClient fileClient;

    @Autowired
    private Config config;

    @Autowired
    private Cryptography cryptography;

    @Autowired
    private DataExtractor dataExtractor;


    ObjectMapper mapper;

    ResourceLoader resourceLoader;

    private Schema schema;
    private final String SCHEMA_PATH = "/Schema.json";

    @Autowired
    public DataProcessingServiceImpl(ResourceLoader resourceLoader, ObjectMapper mapper) {
        this.resourceLoader = resourceLoader;
        this.mapper = mapper;
        this.schema = getSchema();
    }

    private Schema getSchema() {
        try {
            Resource r = resourceLoader.getResource("classpath:/Schema.json");
            return mapper.readValue(Files.readAllBytes(resourceLoader.getResource("classpath:/Schema.json").getFile().toPath()), Schema.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public TransactionsDto extractFilesData(FileDto file) throws GoglotekException, IOException {
        TransactionsDto transactionsDto = new TransactionsDto();

        byte[] encryptedFile = fileClient.getFile(file.getAbsolutePath());
        if (encryptedFile == null) {
            throw new GoglotekException("File Not found");
        }
        byte[] decryptedFile = cryptography.decrypt(encryptedFile, config.getEncryptionKey(), config.getEncryptionInitVector());
        try {
            transactionsDto.setTransactions(dataExtractor.extractTransactions(decryptedFile, this.schema));
            transactionsDto.setFromDate(dataExtractor.getFromDate());
            transactionsDto.setToDate(dataExtractor.getToDate());

            //Move file to processed folder
            fileClient.saveFile(decryptedFile, file.getAbsolutePath(), FileType.PROCESSED);

            //delete file from stage folder
            fileClient.deleteFile(file.getAbsolutePath());
        } catch (Exception e) {
            //send erroneous file to error folder
            fileClient.saveFile(encryptedFile, file.getAbsolutePath(), FileType.ERROR);

            //delete file from stage folder
            fileClient.deleteFile(file.getAbsolutePath());

            //rethrow exception
            throw e;
        }
        return transactionsDto;
    }

}
