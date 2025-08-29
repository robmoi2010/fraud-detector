package com.goglotek.frauddetector.dataprocessorservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class LocalTransactionsMixin {
    @JsonProperty("transactionId")
    private String id;
    @JsonProperty("transactionId")
    private Date transactionTimestamp;
}
