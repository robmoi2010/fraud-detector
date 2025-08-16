package com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto;

public enum DiscrepancyType {
	FRAUDULENT(2), MISSING(1);

	public final int value;

	DiscrepancyType(int i) {
		this.value = i;
	}
}
