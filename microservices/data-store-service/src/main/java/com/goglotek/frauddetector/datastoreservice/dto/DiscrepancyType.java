package com.goglotek.frauddetector.datastoreservice.dto;

public enum DiscrepancyType {
	FRAUDULENT(2), MISSING(1);

	public final int value;

	DiscrepancyType(int i) {
		this.value = i;
	}
}
