package com.goglotek.frauddetector.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.ProviderLocalTransactions;

public class ProviderLocalTransactionsDto {
    private long count;
    private List<ProviderLocalTransactions> providerLocalTransactions;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<ProviderLocalTransactions> getProviderLocalTransactions() {
        return providerLocalTransactions;
    }

    public void setProviderLocalTransactions(List<ProviderLocalTransactions> providerLocalTransactions) {
        this.providerLocalTransactions = providerLocalTransactions;
    }
}
