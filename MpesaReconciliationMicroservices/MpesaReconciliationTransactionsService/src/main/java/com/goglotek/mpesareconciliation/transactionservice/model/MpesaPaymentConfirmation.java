package com.goglotek.mpesareconciliation.transactionservice.model;

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
@Table(name = "mpesa_payment_confirmation")
public class MpesaPaymentConfirmation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6305631756481483362L;
	@Id
	@Column(name = "confirmation_id")
	@SequenceGenerator(name = "confirmation_sequence", sequenceName = "mpesa_payment_confirmation_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_sequence")
	private long confirmationId;
	
	@Column(name = "transtype", nullable = false)
	private String transType;
	
	@Column(name = "transid", nullable = false)
	private String transId;
	
	@Column(name = "transamount", nullable = false)
	private double transAmount;
	
	@Column(name = "businessshortcode", nullable = false)
	private String businessShortcode;
	
	@Column(name = "billrefnumber", nullable = false)
	private String billRefNumber;
	
	@Column(name = "invoicenumber", nullable = true)
	private String invoiceNumber;
	
	@Column(name = "orgaccountbalance", nullable = false)
	private double orgAccountBalance;
	
	@Column(name = "thirdpartytransid", nullable = true)
	private String thirdPartyTransId;
	
	@Column(name = "msisdn", nullable = false)
	private String msisdn;
	
	@Column(name = "lastname", nullable = true)
	private String lastname;
	
	@Column(name = "firstname", nullable = true)
	private String firstname;
	
	@Column(name = "middlename", nullable = true)
	private String middlename;
	
	@Column(name = "transtime", nullable = false)
	private Date transTime;
	
	@Column(name = "sms_sent", nullable = true)
	private boolean smsSent;
	
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	
	@Column(name = "confirmation_status", nullable = true)
	private String confirmationStatus;
	
	@Column(name = "group_id", nullable = false)
	private long groupId;
	
	@Column(name = "company_id", nullable = false)
	private long companyId;
	
	@Column(name = "user_id", nullable = false)
	private long userId;
	
	@Column(name = "user_name", nullable = false)
	private String username;
	
	@Column(name = "recon_comments", nullable = true)
	private String reconComments;
	
	@Column(name = "is_from_reconciliation", nullable = true)
	private boolean isFromReconciliation;

	@Column(name = "is_valid_trans", nullable = true)
	private boolean isValidTrans;

	@Column(name = "fetch_id", nullable = true)
	private long fetchId;

	public String getReconComments() {
		return reconComments;
	}

	public void setReconComments(String reconComments) {
		this.reconComments = reconComments;
	}

	public boolean isValidTrans() {
		return isValidTrans;
	}

	public void setValidTrans(boolean isValidTrans) {
		this.isValidTrans = isValidTrans;
	}

	public long getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(long confirmationId) {
		this.confirmationId = confirmationId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public String getBusinessShortcode() {
		return businessShortcode;
	}

	public void setBusinessShortcode(String businessShortcode) {
		this.businessShortcode = businessShortcode;
	}

	public String getBillRefNumber() {
		return billRefNumber;
	}

	public void setBillRefNumber(String billRefNumber) {
		this.billRefNumber = billRefNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getOrgAccountBalance() {
		return orgAccountBalance;
	}

	public void setOrgAccountBalance(double orgAccountBalance) {
		this.orgAccountBalance = orgAccountBalance;
	}

	public String getThirdPartyTransId() {
		return thirdPartyTransId;
	}

	public void setThirdPartyTransId(String thirdPartyTransId) {
		this.thirdPartyTransId = thirdPartyTransId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public boolean isSmsSent() {
		return smsSent;
	}

	public void setSmsSent(boolean smsSent) {
		this.smsSent = smsSent;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getConfirmationStatus() {
		return confirmationStatus;
	}

	public void setConfirmationStatus(String confirmationStatus) {
		this.confirmationStatus = confirmationStatus;
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

	public boolean isFromReconciliation() {
		return isFromReconciliation;
	}

	public void setFromReconciliation(boolean isFromReconciliation) {
		this.isFromReconciliation = isFromReconciliation;
	}

	public long getFetchId() {
		return fetchId;
	}

	public void setFetchId(long fetchId) {
		this.fetchId = fetchId;
	}

}
