package com.goglotek.frauddetector.dataextractionservice.schema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {
    private String name;

    @JsonProperty("data_type")
    private String dataType;

    @JsonProperty("file_index")
    private int fileIndex;

    @JsonProperty("is_amount")
    private boolean isAmount;

    @JsonProperty("is_transaction_id")
    private boolean isTransactionId;

    @JsonProperty("is_transaction_timestamp")
    private boolean isTransactionTimestamp;

    @JsonProperty("is_provider_client_account")
    private boolean isProviderClientAccount;

    @JsonProperty("is_group_account")
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

    public void setIsAmount(boolean isAmount) {
        this.isAmount = isAmount;
    }

    public boolean isTransactionId() {
        return isTransactionId;
    }

    public void setIsTransactionId(boolean isTransactionId) {
        this.isTransactionId = isTransactionId;
    }

    public boolean isTransactionTimestamp() {
        return isTransactionTimestamp;
    }

    public void setIsTransactionTimestamp(boolean isTransactionTimestamp) {
        this.isTransactionTimestamp = isTransactionTimestamp;
    }

    public boolean isProviderClientAccount() {
        return isProviderClientAccount;
    }

    public void setIsProviderClientAccount(boolean isProviderClientAccount) {
        this.isProviderClientAccount = isProviderClientAccount;
    }

    public boolean isGroupAccount() {
        return isGroupAccount;
    }

    public void setIsGroupAccount(boolean isGroupAccount) {
        this.isGroupAccount = isGroupAccount;
    }
}
