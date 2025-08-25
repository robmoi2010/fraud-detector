package com.goglotek.frauddetector.dataextractionservice.service.impl;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.rest.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DataStoreServiceImplTests extends AbstractTests {
    @Autowired
    private DataStoreServiceImpl dataStoreService;

    @MockitoBean
    private RestClient restClient;

    private String postSuccessMessage = "Successfully sent";
    private FileDto fileDto;
    private List<Transaction> transactionList;

    @BeforeEach
    public void setUp() throws Exception {
        //mock post request
        when(restClient.post(anyString(), anyString())).thenReturn(postSuccessMessage);

        //put mocked restclient in service object

        //create file dto
        fileDto = new FileDto();
        fileDto.setFileId(UUID.randomUUID().toString());
        fileDto.setFileName("transactions.csv");

        //create transactions
        transactionList = new ArrayList<>();
        Transaction t = new Transaction();
        t.setCreatedOn(new Date());
        t.setAmount(100d);
        t.setClientAccount("1235");
        transactionList.add(t);

        Transaction t1 = new Transaction();
        t1.setCreatedOn(new Date());
        t1.setAmount(1000d);
        t1.setClientAccount("1237565");
        transactionList.add(t1);
    }

    @Test
    public void shouldStoreFile() throws GoglotekException {
        String s = dataStoreService.storeFile(fileDto);
        assertTrue(s.equals(postSuccessMessage));
    }

    public void shouldStoreTransactions() throws GoglotekException {
        String s = dataStoreService.storeTransactions(transactionList);
        assertTrue(s.equals(postSuccessMessage));
    }


}
