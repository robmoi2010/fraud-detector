package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model;

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
@Table(name = "mpesa_fetched_trans")
public class MpesaFetchedTrans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2457593276297588753L;
	@Id
	@SequenceGenerator(name = "mpesa_fetch_trans_sequence", sequenceName = "mpesa_fetched_trans_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mpesa_fetch_trans_sequence")
	@Column(name = "fetch_id")
	private Long fetchId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "tracker_id", nullable = true)
	private Long trackerId;
	@Column(name = "receipt_no", nullable = false)
	private String receiptNo;
	@Column(name = "completion_time", nullable = false)
	private Date completionTime;
	@Column(name = "initiation_time", nullable = false)
	private Date initiationTime;
	@Column(name = "details", nullable = false)
	private String details;
	@Column(name = "transaction_status", nullable = false)
	private String transactionStatus;
	@Column(name = "paid_in", nullable = false)
	private Double paidIn;
	@Column(name = "withdrawn", nullable = false)
	private Double withdrawn;
	@Column(name = "balance", nullable = false)
	private Double balance;
	@Column(name = "balance_confirmed", nullable = false)
	private Boolean balanceConfirmed;
	@Column(name = "reason_type", nullable = false)
	private String reasonType;
	@Column(name = "other_party_info", nullable = false)
	private String otherPartyInfo;
	@Column(name = "linked_trans_id", nullable = false)
	private String linkedTransId;
	@Column(name = "account_no", nullable = true)
	private String accountNo;
	@Column(name = "msisdn", nullable = false)
	private String msisdn;
	@Column(name = "system_id", nullable = false)
	private String systemId;
	@Column(name = "shortcode", nullable = false)
	private String shortcode;
	@Column(name = "group_id", nullable = false)
	private Long groupId;
	@Column(name = "company_id", nullable = false)
	private Long companyId;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Column(name = "user_name", nullable = false)
	private String username;
	@Column(name = "firstname", nullable = false)
	private String firstname;
	@Column(name = "middlename", nullable = false)
	private String middlename;
	@Column(name = "lastname", nullable = false)
	private String lastname;
	@Column(name = "file_id", nullable = false)
	private Long fileId;

//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "file_id", insertable = true, updatable = true, nullable = false)
//	private MpesaFiles mpesaFile;

//	public MpesaFiles getMpesaFile() {
//		return mpesaFile;
//	}
//
//	public void setMpesaFile(MpesaFiles mpesaFile) {
//		this.mpesaFile = mpesaFile;
//	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getFetchId() {
		return fetchId;
	}

	public void setFetchId(Long fetchId) {
		this.fetchId = fetchId;
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

	public Long getTrackerId() {
		return trackerId;
	}

	public void setTrackerId(Long trackerId) {
		this.trackerId = trackerId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	public Date getInitiationTime() {
		return initiationTime;
	}

	public void setInitiationTime(Date initiationTime) {
		this.initiationTime = initiationTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Double getPaidIn() {
		return paidIn;
	}

	public void setPaidIn(Double paidIn) {
		this.paidIn = paidIn;
	}

	public Double getWithdrawn() {
		return withdrawn;
	}

	public void setWithdrawn(Double withdrawn) {
		this.withdrawn = withdrawn;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean isBalanceConfirmed() {
		return balanceConfirmed;
	}

	public void setBalanceConfirmed(Boolean balanceConfirmed) {
		this.balanceConfirmed = balanceConfirmed;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getOtherPartyInfo() {
		return otherPartyInfo;
	}

	public void setOtherPartyInfo(String otherPartyInfo) {
		this.otherPartyInfo = otherPartyInfo;
	}

	public String getLinkedTransId() {
		return linkedTransId;
	}

	public void setLinkedTransId(String linkedTransId) {
		this.linkedTransId = linkedTransId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
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

}
