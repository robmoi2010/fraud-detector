package com.goglotek.frauddetector.dataextractionservice.service.impl;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DataProcessingServiceImplTests extends AbstractTests {
    @Autowired
    private DataProcessingServiceImpl dataProcessingService;

    @Autowired
    Cryptography cryptography;

    @Autowired
    Config config;

    private FileDto fileDto;
    private static String fileName = "testfile.csv";


    @BeforeEach
    public void setUp() throws IOException, GoglotekException {
        super.startUp();

        //set schema field to the schema created in super class
        ReflectionTestUtils.setField(dataProcessingService, "schema", schema);

        //create file dto
        fileDto = new FileDto();
        fileDto.setFileId(UUID.randomUUID().toString());
        fileDto.setFileName(fileName);

        //create physical file
        Files.createDirectory(Paths.get(rootFolder));

        Path p = Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName);
        Files.createFile(p);

        //encrypt data from superclass
        byte[] data = cryptography.encrypt(sampleCSV.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector());

        //write encrypted data to the created file
        Files.write(p, data);

        fileDto.setAbsolutePath(p.toAbsolutePath().toString());
    }

    @Test
    public void shouldExtractFileData() throws GoglotekException, IOException {
        TransactionsDto l = dataProcessingService.extractFilesData(fileDto);
        assertTrue(l.getTransactions() != null);
        assertTrue(l.getTransactions().size() == transactionsCount);
        //Most's tests handled in dataExtractor
    }


}
