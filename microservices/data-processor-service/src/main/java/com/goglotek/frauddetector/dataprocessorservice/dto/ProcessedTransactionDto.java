package com.goglotek.frauddetector.dataprocessorservice.dto;

import java.util.Date;
import java.util.List;

public class ProcessedTransactionDto {
    private String fileGlobalId;
    private Date dateProcessed;
    private Date from;
    private Date to;
    private List<Transaction> fraudulentTransactions;
    private List<Transaction> missingTransactions;
    private List<Transaction> invalidAmountTransactions;
    private List<Transaction> invalidClientAccountTransactions;
    private List<Transaction> invalidTimestampTransactions;

    public List<Transaction> getInvalidClientAccountTransactions() {
        return invalidClientAccountTransactions;
    }

    public void setInvalidClientAccountTransactions(List<Transaction> invalidClientAccountTransactions) {
        this.invalidClientAccountTransactions = invalidClientAccountTransactions;
    }

    public List<Transaction> getInvalidTimestampTransactions() {
        return invalidTimestampTransactions;
    }

    public void setInvalidTimestampTransactions(List<Transaction> invalidTimestampTransactions) {
        this.invalidTimestampTransactions = invalidTimestampTransactions;
    }

    public List<Transaction> getInvalidAmountTransactions() {
        return invalidAmountTransactions;
    }

    public void setInvalidAmountTransactions(List<Transaction> invalidAmountTransactions) {
        this.invalidAmountTransactions = invalidAmountTransactions;
    }

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

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
