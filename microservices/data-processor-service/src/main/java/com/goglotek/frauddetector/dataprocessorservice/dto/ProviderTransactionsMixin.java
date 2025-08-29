package com.goglotek.frauddetector.dataprocessorservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class ProviderTransactionsMixin {
    @JsonProperty("transactionId")
    private String id;
    @JsonProperty("transactionTime")
    private Date transactionTimestamp;


}
