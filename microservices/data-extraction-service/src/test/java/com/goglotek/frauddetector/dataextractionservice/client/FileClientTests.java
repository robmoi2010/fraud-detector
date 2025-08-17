package com.goglotek.frauddetector.dataextractionservice.client;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.FileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FileClientTests extends AbstractTests {
    @MockBean
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
        fileClient.saveFile(content, fileName, FileType.UNPROCESSED);
        byte[] b = Files.readAllBytes(Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName));
        assertTrue(b != null);
        assertTrue(fileContent.equals(new String(b)));

        //test get file
        byte[] b2 = fileClient.getFile(rootFolder + FileSystems.getDefault().getSeparator() + fileName);
        assertTrue(b2 != null);
        assertTrue(fileContent.equals(new String(b2)));

        //test delete file
        fileClient.deleteFile(fileName, FileType.UNPROCESSED);
        try {
            byte[] b3 = Files.readAllBytes(Paths.get(rootFolder + FileSystems.getDefault().getSeparator() + fileName));
            assertTrue(b3 == null);
        } catch (Exception e) {

        }

    }
}
