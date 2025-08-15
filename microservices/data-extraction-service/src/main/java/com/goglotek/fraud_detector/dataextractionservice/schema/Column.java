package com.goglotek.fraud_detector.dataextractionservice.schema;

public class Column {
    private String name;
    private String dataType;
    private int fileIndex;
    private boolean isAmount;
    private boolean isTransactionId;
    private boolean isTransactionTimestamp;
    private boolean isProviderClientAccount;
    private boolean isGroupAccount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public boolean isAmount() {
        return isAmount;
    }

    public void setAmount(boolean amount) {
        isAmount = amount;
    }

    public boolean isTransactionId() {
        return isTransactionId;
    }

    public void setTransactionId(boolean transactionId) {
        isTransactionId = transactionId;
    }

    public boolean isTransactionTimestamp() {
        return isTransactionTimestamp;
    }

    public void setTransationTimestamp(boolean transationTimestamp) {
        isTransactionTimestamp = transationTimestamp;
    }

    public void setTransactionTimestamp(boolean transactionTimestamp) {
        isTransactionTimestamp = transactionTimestamp;
    }

    public boolean isProviderClientAccount() {
        return isProviderClientAccount;
    }

    public void setProviderClientAccount(boolean providerClientAccount) {
        isProviderClientAccount = providerClientAccount;
    }

    public boolean isGroupAccount() {
        return isGroupAccount;
    }

    public void setGroupAccount(boolean groupAccount) {
        isGroupAccount = groupAccount;
    }
}
