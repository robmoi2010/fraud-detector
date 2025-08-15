package com.goglotek.frauddetector.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.LocalTransactions;

public class LocalTransactionsDto {
	private long count;
	private List<LocalTransactions> localTransactions;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<LocalTransactions> getLocalTransactions() {
		return localTransactions;
	}

	public void setLocalTransactions(List<LocalTransactions> localTransactions) {
		this.localTransactions = localTransactions;
	}

}
