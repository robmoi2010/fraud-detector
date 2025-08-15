package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

public enum DiscrepancyType {
	FRAUDULENT(2), MISSING(1);

	public final int value;

	DiscrepancyType(int i) {
		this.value = i;
	}
}
