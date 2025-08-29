package com.goglotek.frauddetector.datastoreservice.controller;

import com.goglotek.frauddetector.datastoreservice.AbstractTest;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
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

    @BeforeEach
    public void setUp() throws Exception {
        //create and stub mock objects
        Files respFile = new Files();
        respFile.setFileName(fileName);
        when(filesService.createFile(any(CreateFileDto.class))).thenReturn(respFile);
        when(config.getEncryptionInitVector()).thenReturn(initVector);
        when(config.getEncryptionKey()).thenReturn(encryptionKey);
    }

    @Test
    public void shouldCreateFileSuccessfully() throws Exception {
        String uri = "/files/create";

        String encrypted = Base64.getEncoder().encodeToString(cryptography.encrypt(filePayload.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector()));

        mvc.perform(post(uri).content(encrypted)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(fileName)));
    }

    @Test
    public void shouldFailDueToLackOfPermissions() throws Exception {
        String uri = "/files/create";

        String encrypted = Base64.getEncoder().encodeToString(cryptography.encrypt(filePayload.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector()));

        mvc.perform(post(uri).content(encrypted)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_XYZ"))))
                .andExpect(status().isForbidden()); //forbidden meaning user lacks permissions
    }
}
