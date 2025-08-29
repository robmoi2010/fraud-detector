package com.goglotek.frauddetector.dataextractionservice.extractors;

import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.exception.UnimplementedFeatureException;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.schema.Column;
import com.goglotek.frauddetector.dataextractionservice.schema.Schema;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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

//class not thread safe. Make it thread safe if you plan to extract data from multiple files concurrently or use multiple file queues
@Primary
@Component
public class CsvExtractor implements DataExtractor {
    static final Logger logger = LogManager.getLogger(CsvExtractor.class);
    //TODO: for paying clients, order the array or add a field specifying a format weight or importance so that the most likely or important format get tried first
    private final String[] TIMESTAMP_FORMATS = {
            // Date + Time
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "dd/MM/yyyy HH:mm:ss",
            "MM/dd/yyyy hh:mm:ss a",

            // ISO 8601 variants
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",

            // Compact (for filenames/logs)
            "yyyyMMdd",
            "yyyyMMdd_HHmmss",
            "yyyyMMddHHmmssSSS",

            // Week & Day formats
            "EEE, dd MMM yyyy HH:mm:ss zzz", // RFC 1123

            // Time zone aware
            "yyyy-MM-dd HH:mm:ss Z",
            "yyyy-MM-dd HH:mm:ss.SSS Z",
            "yyyy-MM-dd HH:mm:ss VV"
    };
    private Date fromDate;
    private Date toDate;

    @Override
    public List<Transaction> extractTransactions(byte[] fileData, Schema schema) throws IOException, GoglotekException {
        List<Transaction> transactions = new ArrayList<>();

        //reset toDate and fromDate so that the object can be reused without issues
        this.fromDate = null;
        this.toDate = null;

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
                    processedIndexes.put(index, index);
                    continue;
                }

                //extract transaction id
                if (m.isTransactionId()) {
                    transaction.setId(record.get(index));
                    processedIndexes.put(index, index);
                    continue;
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
                        logger.error(e.getMessage(), e);
                    }
                    //if the provided format fails or is not provided, try common formats
                    if (d == null) {
                        d = processDateString(dateString);
                    }
                    transaction.setTransactionTimestamp(d);

                    //set from and to date based on transaction timestamp
                    if (fromDate == null) {
                        fromDate = d;
                    }
                    if (toDate == null) {
                        toDate = d;
                    }
                    if (d.after(toDate)) {
                        toDate = d;
                    }
                    if (d.before(fromDate)) {
                        fromDate = d;
                    }
                    processedIndexes.put(index, index);
                    continue;
                }

                //extract group account
                if (m.isGroupAccount()) {
                    transaction.setGroupAccount(record.get(index));
                    processedIndexes.put(index, index);
                    continue;
                }

                //extract client account
                if (m.isProviderClientAccount()) {
                    transaction.setClientAccount(record.get(index));
                    processedIndexes.put(index, index);
                }
            }

            //set createdOn
            transaction.setCreatedOn(new Date());

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
                sb.append("\"" + columnName + "\"").append(":").append("\"" + record.get(i) + "\"");
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
        for (String format : TIMESTAMP_FORMATS) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                return formatter.parse(date);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        //throw exception if date object cant be created from string
        throw new GoglotekException("Date format error: can't parse the date " + date);
    }

    @Override
    public Date getFromDate() {
        return fromDate;
    }

    @Override
    public Date getToDate() {
        return toDate;
    }
}
