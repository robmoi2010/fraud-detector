package com.goglotek.frauddetector.dataprocessorservice.dto;

import java.util.Date;

public class Transaction {
    private Long transactionDsId;
    private Date createdOn;
    private String id;
    private String details;
    private Double amount;
    private Date transactionTimestamp;
    private String groupAccount;
    private String clientAccount;

    public Long getTransactionDsId() {
        return transactionDsId;
    }

    public void setTransactionDsId(Long transactionDsId) {
        this.transactionDsId = transactionDsId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Date transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }
}
