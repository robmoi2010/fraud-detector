package com.goglotek.frauddetector.dataextractionservice.extractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CsvExtractorTests extends AbstractTests {
    @Autowired
    CsvExtractor extractor;

    @BeforeEach
    public void setUp() {
        super.startUp();
    }


    @Test
    public void shouldExtractTransactions() throws GoglotekException, IOException {
        List<Transaction> l = extractor.extractTransactions(sampleCSV.getBytes(StandardCharsets.UTF_8), schema);
        assertTrue(l != null);
        assertTrue(l.size() == transactionsCount);

        //check that data is extracted successfully.
        //check random data in the list
        int index = new Random().nextInt(transactionsCount);
        Transaction trans = l.get(index);
        assertTrue(trans.getAmount() == amountMultiplier * (index + 1));
        assertTrue(trans.getClientAccount().equals(clientAccountBase + index));
        assertTrue(trans.getTransactionTimestamp() != null);
        assertTrue(trans.getId().equals(transIdBase + index));

        //ensure that data in details does not contain already extracted static columns
        assertTrue(!trans.getDetails().contains(amountColumnName));
        assertTrue(!trans.getDetails().contains(clientAccountColumnName));

        //check if details contains extra columns
        assertTrue(trans.getDetails().contains(extraColumnName));
        assertTrue(trans.getDetails().contains(extraColumnName1));

        //ensure details is a valid json
        try {
            new ObjectMapper().readTree(trans.getDetails());
            //valid json if exception is not thrown
            assertTrue(true);
        } catch (Exception e) {
            //invalid json if exception is thrown
            e.printStackTrace();
            assertTrue(false);

        }
    }

    @Test
    public void fromDateShouldBeBeforeToDate() throws GoglotekException, IOException {
        List<Transaction> l = extractor.extractTransactions(sampleCSV.getBytes(StandardCharsets.UTF_8), schema);
        assertTrue(l != null && l.size() == transactionsCount);
        assertTrue(extractor.getFromDate() != null && extractor.getToDate() != null);

        //check if from date is less than to date
        assertTrue(extractor.getFromDate().before(extractor.getToDate()));

        //sort list and check whether from and to match first and last items. NB: sorting used only in test because of performance penalty or sorting large data
        Collections.sort(l, (x, y) -> x.getTransactionTimestamp().compareTo(y.getTransactionTimestamp()));
        assertTrue(extractor.getToDate() == l.get(l.size() - 1).getTransactionTimestamp());
        assertTrue(extractor.getFromDate() == l.get(0).getTransactionTimestamp());
    }

}
