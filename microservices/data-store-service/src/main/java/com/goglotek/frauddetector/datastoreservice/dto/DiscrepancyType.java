package com.goglotek.frauddetector.datastoreservice.dto;

public enum DiscrepancyType {
    FRAUDULENT(0), MISSING(1), INVALID_AMOUNT(2), INVALID_CLIENT_ACC(3), INVALID_TIMESTAMP(4);

    public final int value;

    DiscrepancyType(int i) {
        this.value = i;
    }
}
