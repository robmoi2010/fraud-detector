package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaMainTransactions;

public class MpesaMainTransactionsDto {
	private long count;
	private List<MpesaMainTransactions> mpesaMainTransactions;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaMainTransactions> getMpesaMainTransactions() {
		return mpesaMainTransactions;
	}

	public void setMpesaMainTransactions(List<MpesaMainTransactions> mpesaMainTransactions) {
		this.mpesaMainTransactions = mpesaMainTransactions;
	}

}
