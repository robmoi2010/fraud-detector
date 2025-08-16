package com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.ProviderTransactions;

public class ProviderTransactionsDto {
    private long count;
    private List<ProviderTransactions> transactions;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<ProviderTransactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ProviderTransactions> transactions) {
        this.transactions = transactions;
    }

}
