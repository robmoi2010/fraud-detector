package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchedTrans;

public class MpesaFetchedTransDto {
	private long count;
	private List<MpesaFetchedTrans> mpesaTransactions;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaFetchedTrans> getMpesaTransactions() {
		return mpesaTransactions;
	}

	public void setMpesaTransactions(List<MpesaFetchedTrans> mpesaTransactions) {
		this.mpesaTransactions = mpesaTransactions;
	}

}
