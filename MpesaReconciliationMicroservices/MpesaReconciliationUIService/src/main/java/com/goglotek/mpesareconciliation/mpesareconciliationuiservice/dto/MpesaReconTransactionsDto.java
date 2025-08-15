package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconTransactions;

public class MpesaReconTransactionsDto {
	private long count;
	private List<MpesaReconTransactions> mpesaReconTransactions;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaReconTransactions> getMpesaReconTransactions() {
		return mpesaReconTransactions;
	}

	public void setMpesaReconTransactions(List<MpesaReconTransactions> mpesaReconTransactions) {
		this.mpesaReconTransactions = mpesaReconTransactions;
	}
}
