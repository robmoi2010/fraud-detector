package com.goglotek.frauddetector.dataextractionservice.datastoreservice.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "local_transactions")
public class LocalTransactions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6305631756481483362L;
	@Id
	@Column(name = "transaction_ds_id")
	@SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
	private Long transactionDsId;
	@Column(name = "transactionId", nullable = false)
	private String transactionId;
	@Column(name = "amount", nullable = false)
	private Double amount;
	@Column(name = "clientAccount", nullable = false)
	private String clientAccount;
	@Column(name = "transactiontime", nullable = false)
	private Date transactionTime;
	@Column(name = "created_on", nullable = false)
	private Date createdOn;

	public Long getTransactionDsId() {
		return transactionDsId;
	}

	public void setTransactionDsId(Long transactionDsId) {
		this.transactionDsId = transactionDsId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getClientAccount() {
		return clientAccount;
	}

	public void setClientAccount(String clientAccount) {
		this.clientAccount = clientAccount;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
