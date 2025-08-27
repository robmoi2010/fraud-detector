package com.goglotek.frauddetector.datastoreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.AbstractTest;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import com.goglotek.frauddetector.datastoreservice.service.ProviderTransactionsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProviderTransactionsControllerTests extends AbstractTest {
    @MockitoSpyBean
    private Config config;

    @MockitoBean
    private FilesService filesService;

    @MockitoBean
    private ProviderTransactionsService providerTransactionsService;


    @Autowired
    private Cryptography cryptography;

    @Autowired
    private ObjectMapper objectMapper;

    private String fileName = "file123.csv";
    private String encryptionKey = "key1234567890987";
    private String initVector = "vector1234567890";
    private String fileId = "123123123434234";
    private List<CreateProviderTransactionsDto> transactions = new ArrayList<>();
    private final int TOTAL_TRANS = 10;

    @BeforeAll
    public void setUp() throws GoglotekException {
        transactions = createTransactions();
    }

    @Test
    public void shouldSendTransactionsSuccessfully() throws Exception {
        //set up mock objects
        Files file = new Files();
        file.setFileName(fileName);
        file.setGlobalId(fileId);

        when(filesService.getFileByGlobalId(anyString())).thenReturn(file);

        when(config.getEncryptionInitVector()).thenReturn(initVector);
        when(config.getEncryptionKey()).thenReturn(encryptionKey);

        doNothing().when(providerTransactionsService).createAll(anyList(), any());


        String uri = "/providertransactions/create/{file_global_id}";
        String trans = objectMapper.writeValueAsString(transactions);
        String encrypted = Base64.getEncoder().encodeToString(cryptography.encrypt(trans.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector()));

        mvc.perform(post(uri, fileId).header("Authorization", "Bearer " + token)
                        .content(encrypted).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")));
    }

    @Test
    public void shouldFailDueToLackOfPermissions() throws Exception {
        String uri = "/providertransactions/create/{file_global_id}";

        String trans = objectMapper.writeValueAsString(transactions);
        String encrypted = Base64.getEncoder().encodeToString(cryptography.encrypt(trans.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector()));

        mvc.perform(post(uri, fileId).header("Authorization", "Bearer " + tokenWithoutRoles)
                        .content(encrypted).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    private List<CreateProviderTransactionsDto> createTransactions() {
        List<CreateProviderTransactionsDto> list = new ArrayList<>();
        for (int i = 0; i < TOTAL_TRANS; i++) {
            CreateProviderTransactionsDto trans = new CreateProviderTransactionsDto();
            trans.setAmount((i + 1) * 100d);
            trans.setCreatedOn(new Date());
            trans.setDetails("details");
            trans.setClientAccount("12345" + i);
            trans.setTransactionTimestamp(new Date());
            trans.setGroupAccount("12345");
            list.add(trans);
        }
        return list;
    }
}
