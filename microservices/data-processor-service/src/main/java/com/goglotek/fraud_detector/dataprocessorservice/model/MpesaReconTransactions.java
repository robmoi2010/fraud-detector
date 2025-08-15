package com.goglotek.fraud_detector.dataprocessorservice.model;

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
@Table(name = "mpesa_recon_transactions")
public class MpesaReconTransactions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6648374268207755798L;
	@Id
	@Column(name = "recon_id")
	@SequenceGenerator(name = "recon_trans_sequence", sequenceName = "mpesa_recon_transactions_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recon_trans_sequence")
	private long reconId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "is_valid_trans", nullable = false)
	private boolean isValidTrans;
	@Column(name = "discrepancy_type", nullable = true)
	private int dicrepancyType;
	@Column(name = "is_inserted_to_main", nullable = false)
	private boolean isInsertedToMain;
	@Column(name = "is_flagged_in_main", nullable = false)
	private boolean isFlaggedInMain;
	@Column(name = "comments", nullable = false)
	private String comments;
	@Column(name = "confirmation_id", nullable = false)
	private long confirmationId;
	@Column(name = "fetch_id", nullable = false)
	private long fetchId;
	@Column(name = "system_id", nullable = false)
	private String systemId;
	@Column(name = "reconciliation_id", nullable = false)
	private long reconciliationId;
	@Column(name = "group_id", nullable = false)
	private long groupId;
	@Column(name = "company_id", nullable = false)
	private long companyId;
	@Column(name = "user_id", nullable = false)
	private long userId;
	@Column(name = "user_name", nullable = false)
	private String username;
	@Column(name = "action", nullable = true)
	private String action;

	public long getReconId() {
		return reconId;
	}

	public void setReconId(long reconId) {
		this.reconId = reconId;
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

	public boolean isValidTrans() {
		return isValidTrans;
	}

	public void setValidTrans(boolean isValidTrans) {
		this.isValidTrans = isValidTrans;
	}

	public int getDicrepancyType() {
		return dicrepancyType;
	}

	public void setDicrepancyType(int dicrepancyType) {
		this.dicrepancyType = dicrepancyType;
	}

	public boolean isInsertedToMain() {
		return isInsertedToMain;
	}

	public void setInsertedToMain(boolean isInsertedToMain) {
		this.isInsertedToMain = isInsertedToMain;
	}

	public boolean isFlaggedInMain() {
		return isFlaggedInMain;
	}

	public void setFlaggedInMain(boolean isFlaggedInMain) {
		this.isFlaggedInMain = isFlaggedInMain;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public long getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(long confirmationId) {
		this.confirmationId = confirmationId;
	}

	public long getFetchId() {
		return fetchId;
	}

	public void setFetchId(long fetchId) {
		this.fetchId = fetchId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public long getReconciliationId() {
		return reconciliationId;
	}

	public void setReconciliationId(long reconciliationId) {
		this.reconciliationId = reconciliationId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
