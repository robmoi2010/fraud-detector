package com.goglotek.frauddetector.dataprocessorservice;


import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public abstract class AbstractTests {
    @MockBean
    protected DataStoreService dataStoreService;
    protected List<Transaction> localTransactions;
    protected List<Transaction> providerTransactions;
    protected int localTransCount = 15;
    protected int providerTransCount = 10;
    private Date timestamp;

    public void startUp() {
        timestamp = new Date();
        //create local transactions
        localTransactions = createLocalTransactions();

        //create provider transactions
        providerTransactions = createProviderTransactions();

        //mock data store service
        when(dataStoreService.getLocalTransactions(any(FileDto.class))).thenReturn(localTransactions);
        when(dataStoreService.getProviderTransactions(any(FileDto.class))).thenReturn(providerTransactions);
        doNothing().when(dataStoreService).storeProcessedTransactionData(any(ProcessedTransactionDto.class));
    }

    private List<Transaction> createLocalTransactions() {
        List<Transaction> t = new ArrayList<>();
        for (int i = 0; i < localTransCount; i++) {
            Transaction tr = new Transaction();
            tr.setCreatedOn(new Date());
            tr.setAmount((i + 1) * 100d);
            tr.setId("id" + (i + 1));
            tr.setClientAccount("12345" + i);
            Calendar c = Calendar.getInstance();
            c.setTime(timestamp);
            c.add(Calendar.HOUR, i);
            tr.setTransactionTimestamp(c.getTime());
            t.add(tr);
        }
        return t;
    }

    private List<Transaction> createProviderTransactions() {
        List<Transaction> t = new ArrayList<>();
        for (int i = 0; i < providerTransCount; i++) {
            Transaction tr = new Transaction();
            tr.setCreatedOn(new Date());
            tr.setAmount((i + 1) * 100d);
            tr.setId("id" + (i + 1));
            tr.setClientAccount("12345" + i);
            Calendar c = Calendar.getInstance();
            c.setTime(timestamp);
            c.add(Calendar.HOUR, i);
            tr.setTransactionTimestamp(c.getTime());
            t.add(tr);
        }
        return t;
    }
}
