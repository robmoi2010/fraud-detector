package com.goglotek.fraud_detector.dataextractionservice.extractors;

import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.exception.UnimplementedFeatureException;
import com.goglotek.fraud_detector.dataextractionservice.model.Transaction;
import com.goglotek.fraud_detector.dataextractionservice.schema.Column;
import com.goglotek.fraud_detector.dataextractionservice.schema.Schema;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Primary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Primary
public class CsvExtractor implements DataExtractor {
    private final String[] DATE_FORMATS = {
            "dd/MM/yyyy mm:ss",
            "dd-MM-yyyy mm:ss",
            "MM/dd/yyyy mm:ss",
            "MM-dd-yyyy mm:ss",
            "dd MM yyyy mm:ss",
            "MM dd yyyy mm:ss"
    };

    @Override
    public List<Transaction> extractTransactions(byte[] fileData, Schema schema) throws IOException, GoglotekException {
        List<Transaction> transactions = new ArrayList<>();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData)) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                if (schema.hasHeaders()) {
                    Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
                    for (CSVRecord record : records) {
                        transactions.add(processRecord(record, schema));
                    }
                } else {
                    //TODO: to be implemented for paying clients
                    throw new UnimplementedFeatureException("Extracting csv file with no headers is not implemented yet. Contract Goglotek for implementation");
                }
            }
        }
        return transactions;
    }

    private Transaction processRecord(CSVRecord record, Schema schema) throws GoglotekException {
        Transaction transaction = new Transaction();
        if (schema.hasHeaders()) {
            Map<String, Integer> headerMap = record.getParser().getHeaderMap();
            Map<Integer, Integer> processedIndexes = new HashMap<>();
            for (Column m : schema.getColumns()) {
                int index = headerMap.get(m.getName());

                //extract amount
                if (m.isAmount()) {
                    transaction.setAmount(Double.parseDouble(record.get(index)));
                }

                //extract transaction id
                if (m.isTransactionId()) {
                    transaction.setId(record.get(index));
                }

                //extract timestamp
                if (m.isTransactionTimestamp()) {
                    String dateString = record.get(index);
                    Date d = null;
                    //first try using date format provided in schema to parse the date
                    try {
                        if (dateString != null && !dateString.isEmpty()) {
                            d = new SimpleDateFormat(schema.getDateFormat()).parse(dateString);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //if the provided format fails or is not provided, try common formats
                    if (d == null) {
                        d = processDateString(dateString);
                    }
                    transaction.setTransactionTimestamp(d);
                }

                //extract group account
                if (m.isGroupAccount()) {
                    transaction.setGroupAccount(record.get(index));
                }

                //extract client account
                if (m.isProviderClientAccount()) {
                    transaction.setClientAccount(record.get(index));
                }
                processedIndexes.put(index, index);
            }
            //TODO: have a configuration that enables or disables storing extra data that is not required in fraud detection process.
            //put the rest of data in details in json format for future ease of processing
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int i = 0; i < record.size(); i++) {
                if (processedIndexes.get(i) != null) {
                    //skip already processed columns
                    continue;
                }
                //get column name
                String columnName = "";
                for (Map.Entry<String, Integer> entry : headerMap.entrySet()) {
                    if (entry.getValue() == i) {
                        columnName = entry.getKey();
                        break;
                    }
                }
                sb.append(columnName).append(":").append(record.get(i));
                if (i != record.size() - 1) {
                    sb.append(",");
                }

            }
            sb.append("}");
            transaction.setDetails(sb.toString());
            return transaction;
        } else {
            //TODO: to be implemented for paying clients
            throw new UnimplementedFeatureException("Extracting csv file with no headers is not implemented yet. Contract Goglotek for implementation");
        }
    }

    private Date processDateString(String date) throws GoglotekException {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                return formatter.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //throw exception if date object cant be created from string
        throw new GoglotekException("Date format error: can't parse the date " + date);
    }
}
