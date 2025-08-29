/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.dataextractionservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

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
    byte[] data = cryptography.encrypt(sampleCSV.getBytes(), config.getEncryptionKey(),
        config.getEncryptionInitVector());

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
