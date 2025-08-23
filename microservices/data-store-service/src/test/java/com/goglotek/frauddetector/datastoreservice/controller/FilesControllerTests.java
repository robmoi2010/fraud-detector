package com.goglotek.frauddetector.datastoreservice.controller;

import com.goglotek.frauddetector.datastoreservice.AbstractTest;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class FilesControllerTests extends AbstractTest {
    @Autowired
    Config config;

    @MockitoBean
    private FilesService filesService;

    @MockitoBean
    private Cryptography cryptography;

    private String filePayload = "{" +
            "     \"fileName\":\"file123.csv\"," +
            "     \"absolutePath\":\"\"," +
            "     \"createdOn\":\"\"," +
            "     \"fileId\":\"12345\"," +
            "     \"totalTransactions\":20," +
            "     \"groupAccount\":\"\"," +
            "     \"fromDate\":\"\"," +
            "     \"toDate\":\"\"" +
            "}";

    @BeforeEach
    public void setUp() throws Exception {
        //super.startUp();
        when(filesService.createFile(any(CreateFileDto.class))).thenReturn(new Files());
        when(cryptography.encrypt(any(byte[].class), anyString())).thenReturn(filePayload.getBytes());
        when(cryptography.decrypt(any(byte[].class), anyString())).thenReturn(filePayload.getBytes());
    }

    @Test
    public void shouldCreateFileSuccessfully() throws Exception {
        String uri = "/files/create";
        byte[] encrypted = cryptography.encrypt(filePayload.getBytes(), config.getEncryptionKey());

        //
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Arrays.asList("Bearer " + token));
        headers.put("Content-Type", Arrays.asList("application/json"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.putAll(headers);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(baseUrl + uri).headers(httpHeaders).content(new String(encrypted)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String results = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(results != null);
    }
}
