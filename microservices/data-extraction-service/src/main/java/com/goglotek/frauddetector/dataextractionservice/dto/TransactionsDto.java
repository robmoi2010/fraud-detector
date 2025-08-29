package com.goglotek.frauddetector.dataextractionservice.dto;

import java.util.Date;
import java.util.List;

public class TransactionsDto {
    private Date fromDate;
    private Date toDate;
    List<Transaction> transactions;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
