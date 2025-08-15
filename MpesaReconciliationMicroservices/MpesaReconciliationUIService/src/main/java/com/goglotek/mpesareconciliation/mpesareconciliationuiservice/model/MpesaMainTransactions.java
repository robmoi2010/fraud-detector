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
@Table(name = "mpesa_payment_confirmation")
public class MpesaMainTransactions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6305631756481483362L;
	@Id
	@Column(name = "confirmation_id")
	@SequenceGenerator(name = "confirmation_sequence", sequenceName = "mpesa_payment_confirmation_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_sequence")
	private Long confirmationId;
	@Column(name = "transtype", nullable = false)
	private String transType;
	@Column(name = "transid", nullable = false)
	private String transId;
	@Column(name = "transamount", nullable = false)
	private Double transAmount;
	@Column(name = "businessshortcode", nullable = false)
	private String businessShortcode;
	@Column(name = "billrefnumber", nullable = true)
	private String billRefNumber;
	@Column(name = "invoicenumber", nullable = true)
	private String invoiceNumber;
	@Column(name = "orgaccountbalance", nullable = true)
	private Double orgAccountBalance;
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
	private Boolean smsSent;
	@Column(name = "created_on", nullable = false)
	private Date createdOn;
	@Column(name = "confirmation_status", nullable = true)
	private String confirmationStatus;
	@Column(name = "group_id", nullable = false)
	private Long groupId;
	@Column(name = "company_id", nullable = false)
	private Long companyId;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Column(name = "user_name", nullable = false)
	private String username;
	@Column(name = "is_from_reconciliation", nullable = false)
	private Boolean isFromReconciliation;
	@Column(name = "fetch_id", nullable = true)
	private Long fetchId;

	public Long getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(Long confirmationId) {
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

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
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

	public Double getOrgAccountBalance() {
		return orgAccountBalance;
	}

	public void setOrgAccountBalance(Double orgAccountBalance) {
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

	public Boolean isSmsSent() {
		return smsSent;
	}

	public void setSmsSent(Boolean smsSent) {
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

	public Boolean isFromReconciliation() {
		return isFromReconciliation;
	}

	public void setFromReconciliation(Boolean isFromReconciliation) {
		this.isFromReconciliation = isFromReconciliation;
	}

	public Long getFetchId() {
		return fetchId;
	}

	public void setFetchId(Long fetchId) {
		this.fetchId = fetchId;
	}

}
