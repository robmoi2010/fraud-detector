package com.goglotek.frauddetector.datastoreservice.dto;

import java.util.Date;
import java.util.List;

public class ProcessedTransactionDto {
    private String fileGlobalId;
    private Date dateProcessed;
    private Date from;
    private Date to;
    private List<ProcessedTransaction> fraudulentTransactions;
    private List<ProcessedTransaction> missingTransactions;
    private List<ProcessedTransaction> invalidAmountTransactions;
    private List<ProcessedTransaction> invalidClientAccountTransactions;
    private List<ProcessedTransaction> invalidTimestampTransactions;

    public List<ProcessedTransaction> getInvalidClientAccountTransactions() {
        return invalidClientAccountTransactions;
    }

    public void setInvalidClientAccountTransactions(List<ProcessedTransaction> invalidClientAccountTransactions) {
        this.invalidClientAccountTransactions = invalidClientAccountTransactions;
    }

    public List<ProcessedTransaction> getInvalidTimestampTransactions() {
        return invalidTimestampTransactions;
    }

    public void setInvalidTimestampTransactions(List<ProcessedTransaction> invalidTimestampTransactions) {
        this.invalidTimestampTransactions = invalidTimestampTransactions;
    }

    public List<ProcessedTransaction> getInvalidAmountTransactions() {
        return invalidAmountTransactions;
    }

    public void setInvalidAmountTransactions(List<ProcessedTransaction> invalidAmountTransactions) {
        this.invalidAmountTransactions = invalidAmountTransactions;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public List<ProcessedTransaction> getFraudulentTransactions() {
        return fraudulentTransactions;
    }

    public void setFraudulentTransactions(List<ProcessedTransaction> fraudulentTransactions) {
        this.fraudulentTransactions = fraudulentTransactions;
    }

    public List<ProcessedTransaction> getMissingTransactions() {
        return missingTransactions;
    }

    public void setMissingTransactions(List<ProcessedTransaction> missingTransactions) {
        this.missingTransactions = missingTransactions;
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

    public String getFileGlobalId() {
        return fileGlobalId;
    }

    public void setFileGlobalId(String fileGlobalId) {
        this.fileGlobalId = fileGlobalId;
    }
}
