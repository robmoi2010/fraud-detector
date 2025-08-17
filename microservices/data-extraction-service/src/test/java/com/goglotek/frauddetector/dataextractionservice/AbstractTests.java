package com.goglotek.frauddetector.dataextractionservice;

import com.goglotek.frauddetector.dataextractionservice.schema.Column;
import com.goglotek.frauddetector.dataextractionservice.schema.Schema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public abstract class AbstractTests {

    protected Schema schema;
    protected String amountColumnName = "AMOUNT";
    protected String groupAccColumnName = "GROUP_ACC";
    protected String transIdColumnName = "TRANS_ID";
    protected String clientAccountColumnName = "ACCOUNT";
    protected String timeColumnName = "TIMESTAMP";
    protected String sampleCSV = "";
    protected String dateFormat = "dd/MM/yyyy HH:mm:ss";
    protected int transactionsCount = 10;
    protected int amountMultiplier = 1000;
    protected String groupAccount = "12345";
    protected String transIdBase = "83347";
    protected String clientAccountBase = "567567567";
    protected String extraColumnName = "EXTRA";
    protected String extraColumnName1 = "EXTRA 1";
    protected static String rootFolder = "tests";

    @BeforeEach
    public void startUp() {
        schema = new Schema();
        schema.setFileType("csv");
        schema.setDateFormat(dateFormat);
        schema.setHasHeaders(true);

        Column[] columns = getColumns();
        schema.setColumns(columns);

        //create sample data file
        StringBuilder sb = new StringBuilder();
        //add headers
        Arrays.stream(columns).forEach(s -> {
            sb.append(s.getName()).append(",");
        });

        //dirty way to remove last ',' character
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(System.lineSeparator());
        for (int i = 0; i < transactionsCount; i++) {
            sb.append((i + 1) * amountMultiplier).append(",");
            sb.append(groupAccount).append(",");
            sb.append(transIdBase + i).append(",");

            Calendar c = Calendar.getInstance();
            //to put big gap between transactions timestamp
            c.add(Calendar.HOUR, i);
            sb.append(new SimpleDateFormat(schema.getDateFormat()).format(c.getTime())).append(",");

            sb.append(clientAccountBase + i).append(",");
            sb.append("Extra data " + (i + 1)).append(",");
            sb.append("Extra data1 " + (i + 1));
            sb.append(System.lineSeparator());
        }
        sampleCSV = sb.toString();
    }

    protected Column[] getColumns() {
        Column col = new Column();
        col.setIsAmount(true);
        col.setDataType("number");
        col.setName(amountColumnName);

        Column col1 = new Column();
        col1.setIsGroupAccount(true);
        col1.setDataType("string");
        col1.setName(groupAccColumnName);

        Column col2 = new Column();
        col2.setIsTransactionId(true);
        col2.setDataType("string");
        col2.setName(transIdColumnName);

        Column col3 = new Column();
        col3.setIsTransactionTimestamp(true);
        col3.setDataType("string");
        col3.setName(timeColumnName);

        Column col4 = new Column();
        col4.setIsProviderClientAccount(true);
        col4.setDataType("string");
        col4.setName(clientAccountColumnName);

        Column col5 = new Column();
        col5.setDataType("string");
        col5.setName(extraColumnName);

        Column col6 = new Column();
        col6.setDataType("string");
        col6.setName(extraColumnName1);

        Column[] columns = new Column[]{col, col1, col2, col3, col4, col5, col6};
        return columns;
    }

    @AfterAll
    public static void tearDown() throws IOException {
        try {
            //delete temp test folder
            Files.walkFileTree(Paths.get(rootFolder), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e)
                        throws IOException {
                    if (e == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        // directory iteration failed
                        throw e;
                    }
                }
            });
        } catch (NoSuchFileException e) {

        }
    }
}
