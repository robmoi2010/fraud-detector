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

package com.goglotek.frauddetector.dataextractionservice.client;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.dto.FileType;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FileClientTests extends AbstractTests {

  @MockitoSpyBean
  Config config;

  @Autowired
  FileClient fileClient;

  private static String fileName = "testfile.txt";
  private static String fileContent = "test data";

  @BeforeEach
  public void setUp() {
    when(config.getErrorDir()).thenReturn(rootFolder);
    when(config.getProcessedDir()).thenReturn(rootFolder);
    when(config.getStagingDir()).thenReturn(rootFolder);
    when(config.getUnauthorizedDir()).thenReturn(rootFolder);
  }

  @Test
  public void shouldCreateRetrieveAndDeleteFile() throws GoglotekException, IOException {
    //tests save file
    byte[] content = fileContent.getBytes();
    fileClient.saveFile(content,
        Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName).toAbsolutePath()
            .toString(), FileType.UNPROCESSED);
    byte[] b = Files.readAllBytes(
        Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName));
    assertTrue(b != null);
    assertTrue(fileContent.equals(new String(b)));

    //test get file
    byte[] b2 = fileClient.getFile(rootFolder + FileSystems.getDefault().getSeparator() + fileName);
    assertTrue(b2 != null);
    assertTrue(fileContent.equals(new String(b2)));

    //test delete file
    fileClient.deleteFile(
        Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName).toAbsolutePath()
            .toString());
    try {
      byte[] b3 = Files.readAllBytes(
          Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName));
      assertTrue(b3 == null);
    } catch (Exception e) {

    }

  }
}
