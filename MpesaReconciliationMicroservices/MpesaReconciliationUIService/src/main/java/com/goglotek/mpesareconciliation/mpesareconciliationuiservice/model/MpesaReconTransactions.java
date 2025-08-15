package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model;

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
	private Long reconId;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;

	@Column(name = "is_valid_trans", nullable = false)
	private Boolean isValidTrans;

	@Column(name = "discrepancy_type", nullable = true)
	private Integer discrepancyType;

	@Column(name = "is_inserted_to_main", nullable = false)
	private Boolean isInsertedToMain;

	@Column(name = "is_flagged_in_main", nullable = false)
	private Boolean isFlaggedInMain;

	@Column(name = "comments", nullable = false)
	private String comments;

	@Column(name = "system_id", nullable = false)
	private String systemId;

	@Column(name = "group_id", nullable = false)
	private Long groupId;

	@Column(name = "company_id", nullable = false)
	private Long companyId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "user_name", nullable = false)
	private String username;

	@Column(name = "action", nullable = true)
	private String action;

	@Column(name = "reconciliation_id", nullable = false)
	private Long reconciliationId;

	@OneToOne
	@JoinColumn(name = "confirmation_id", insertable = false, updatable = false, nullable = true)
	private MpesaMainTransactions mpesaMainTransactions;

	@OneToOne
	@JoinColumn(name = "fetch_id", insertable = false, updatable = false, nullable = true)
	private MpesaFetchedTrans mpesaFetchedTrans;

	public MpesaMainTransactions getMpesaMainTransactions() {
		return mpesaMainTransactions;
	}

	public void setMpesaMainTransactions(MpesaMainTransactions mpesaMainTransactions) {
		this.mpesaMainTransactions = mpesaMainTransactions;
	}

	public Long getReconciliationId() {
		return reconciliationId;
	}

	public void setReconciliationId(Long reconciliationId) {
		this.reconciliationId = reconciliationId;
	}

	public MpesaFetchedTrans getMpesaFetchedTrans() {
		return mpesaFetchedTrans;
	}

	public void setMpesaFetchedTrans(MpesaFetchedTrans mpesaFetchedTrans) {
		this.mpesaFetchedTrans = mpesaFetchedTrans;
	}

	public Long getReconId() {
		return reconId;
	}

	public void setReconId(Long reconId) {
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

	public Boolean isValidTrans() {
		return isValidTrans;
	}

	public void setValidTrans(Boolean isValidTrans) {
		this.isValidTrans = isValidTrans;
	}

	public Boolean getIsValidTrans() {
		return isValidTrans;
	}

	public void setIsValidTrans(Boolean isValidTrans) {
		this.isValidTrans = isValidTrans;
	}

	public Integer getDiscrepancyType() {
		return discrepancyType;
	}

	public void setDiscrepancyType(Integer discrepancyType) {
		this.discrepancyType = discrepancyType;
	}

	public Boolean getIsInsertedToMain() {
		return isInsertedToMain;
	}

	public void setIsInsertedToMain(Boolean isInsertedToMain) {
		this.isInsertedToMain = isInsertedToMain;
	}

	public Boolean getIsFlaggedInMain() {
		return isFlaggedInMain;
	}

	public void setIsFlaggedInMain(Boolean isFlaggedInMain) {
		this.isFlaggedInMain = isFlaggedInMain;
	}

	public Boolean isInsertedToMain() {
		return isInsertedToMain;
	}

	public void setInsertedToMain(Boolean isInsertedToMain) {
		this.isInsertedToMain = isInsertedToMain;
	}

	public Boolean isFlaggedInMain() {
		return isFlaggedInMain;
	}

	public void setFlaggedInMain(Boolean isFlaggedInMain) {
		this.isFlaggedInMain = isFlaggedInMain;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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
