package com.goglotek.frauddetector.datastoreservice.controller;

import com.goglotek.frauddetector.datastoreservice.AbstractTest;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FilesControllerTests extends AbstractTest {

    @MockitoSpyBean
    private Config config;

    @MockitoBean
    private FilesService filesService;

    @Autowired
    private Cryptography cryptography;

    private String fileName = "file123.csv";
    private String encryptionKey = "key1234567890987";
    private String initVector = "vector1234567890";
    private String fileId = UUID.randomUUID().toString();

    private String filePayload = "{" +
            "     \"fileName\":\"" + fileName + "\"," +
            "     \"absolutePath\":\"\"," +
            "     \"createdOn\":\"\"," +
            "     \"fileId\":\"" + fileId + "\"," +
            "     \"totalTransactions\":20," +
            "     \"groupAccount\":\"\"," +
            "     \"fromDate\":\"\"," +
            "     \"toDate\":\"\"" +
            "}";

    @BeforeAll
    public void setUp() throws Exception {

    }

    @Test
    public void shouldCreateFileSuccessfully() throws Exception {
        String uri = "/files/create";

        //create and stub mock objects
        Files respFile = new Files();
        respFile.setFileName(fileName);
        when(filesService.createFile(any(CreateFileDto.class))).thenReturn(respFile);
        when(config.getEncryptionInitVector()).thenReturn(initVector);
        when(config.getEncryptionKey()).thenReturn(encryptionKey);

        String encrypted = Base64.getEncoder().encodeToString(cryptography.encrypt(filePayload.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector()));

        mvc.perform(post(uri).header("Authorization", "Bearer " + token)
                        .content(encrypted).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(fileName)));
    }

    @Test
    public void shouldFailDueToLackOfPermissions() throws Exception {
        String uri = "/files/create";

        String encrypted = Base64.getEncoder().encodeToString(cryptography.encrypt(filePayload.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector()));

        mvc.perform(post(uri).header("Authorization", "Bearer " + tokenWithoutRoles)
                        .content(encrypted).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());//forbidden meaning user lacks permissions
    }
}
