package com.goglotek.frauddetector.datastoreservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "provider_local_transactions")
public class ProviderLocalTransactions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6648374268207755798L;
	@Id
	@Column(name = "provider_local_id")
	@SequenceGenerator(name = "provider_local_sequence", sequenceName = "provider_local_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_local_sequence")
	private Long providerLocalId;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;

	@Column(name = "is_valid_transaction", nullable = false)
	private Boolean isValidTransaction;

	@Column(name = "discrepancy_type", nullable = true)
	private Integer discrepancyType;

	@Column(name = "is_pushed_to_local", nullable = false)
	private Boolean isPushedToLocal;

	@Column(name = "is_flagged_in_local", nullable = false)
	private Boolean isFlaggedInLocal;

	@Column(name = "comments", nullable = false)
	private String comments;

	@Column(name = "action", nullable = true)
	private String action;

	@Column(name = "processing_id", nullable = false)
	private Long processingId;

	@OneToOne
	@JoinColumn(name = "local_transactions_id", insertable = false, updatable = false, nullable = true)
	private LocalTransactions localTransactions;

	@OneToOne
	@JoinColumn(name = "provider_transactions_id", insertable = false, updatable = false, nullable = true)
	private ProviderTransactions providerTransactions;

	public Long getProviderLocalId() {
		return providerLocalId;
	}

	public void setProviderLocalId(Long providerLocalId) {
		this.providerLocalId = providerLocalId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Boolean getValidTransaction() {
		return isValidTransaction;
	}

	public void setValidTransaction(Boolean validTransaction) {
		isValidTransaction = validTransaction;
	}

	public Integer getDiscrepancyType() {
		return discrepancyType;
	}

	public void setDiscrepancyType(Integer discrepancyType) {
		this.discrepancyType = discrepancyType;
	}

	public Boolean getPushedToLocal() {
		return isPushedToLocal;
	}

	public void setPushedToLocal(Boolean pushedToLocal) {
		isPushedToLocal = pushedToLocal;
	}

	public Boolean getFlaggedInLocal() {
		return isFlaggedInLocal;
	}

	public void setFlaggedInLocal(Boolean flaggedInLocal) {
		isFlaggedInLocal = flaggedInLocal;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getProcessingId() {
		return processingId;
	}

	public void setProcessingId(Long processingId) {
		this.processingId = processingId;
	}

	public LocalTransactions getLocalTransactions() {
		return localTransactions;
	}

	public void setLocalTransactions(LocalTransactions localTransactions) {
		this.localTransactions = localTransactions;
	}

	public ProviderTransactions getProviderTransactions() {
		return providerTransactions;
	}

	public void setProviderTransactions(ProviderTransactions providerTransactions) {
		this.providerTransactions = providerTransactions;
	}
}
