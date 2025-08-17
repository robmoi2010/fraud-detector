package com.goglotek.frauddetector.dataprocessorservice.dto;

import java.util.Date;
import java.util.List;

public class ProcessedTransactionDto {
    private String fileGlobalId;
    private Date dateProcessed;
    private List<Transaction> fraudulentTransactions;
    private List<Transaction> missingTransactions;

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public List<Transaction> getFraudulentTransactions() {
        return fraudulentTransactions;
    }

    public void setFraudulentTransactions(List<Transaction> fraudulentTransactions) {
        this.fraudulentTransactions = fraudulentTransactions;
    }

    public List<Transaction> getMissingTransactions() {
        return missingTransactions;
    }

    public void setMissingTransactions(List<Transaction> missingTransactions) {
        this.missingTransactions = missingTransactions;
    }

    public String getFileGlobalId() {
        return fileGlobalId;
    }

    public void setFileGlobalId(String fileGlobalId) {
        this.fileGlobalId = fileGlobalId;
    }
}
