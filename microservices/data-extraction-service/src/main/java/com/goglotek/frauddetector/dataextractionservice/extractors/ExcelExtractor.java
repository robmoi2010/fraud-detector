package com.goglotek.frauddetector.dataextractionservice.extractors;

import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.schema.Schema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//TODO: Implement for paying clients
@Component
public class ExcelExtractor implements DataExtractor{
    @Override
    public List<Transaction> extractTransactions(byte[] data, Schema schema) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public Date getFromDate() {
        return null;
    }

    @Override
    public Date getToDate() {
        return null;
    }
}
