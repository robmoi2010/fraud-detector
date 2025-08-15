package com.goglotek.mpesareconciliation.handlerservice.model;

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
@Table(name = "mpesa_files")
public class MpesaFiles implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 251569524834023530L;
	@Id
	@Column(name = "file_id")
	@SequenceGenerator(name = "uploads_sequence", sequenceName = "mpesa_files_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uploads_sequence")
	private long fileId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "filename", nullable = false)
	private String fileName;
	@Column(name = "trans_count", nullable = false)
	private int transCount;
	@Column(name = "processed", nullable = false)
	private boolean processed;
	@Column(name = "date_processed", nullable = true)
	private Date dateProcessed;
	@Column(name = "account_holder", nullable = false)
	private String accountHolder;
	@Column(name = "shortcode", nullable = false)
	private String shortcode;
	@Column(name = "account", nullable = false)
	private String account;
	@Column(name = "from_date", nullable = true)
	private Date fromDate;
	@Column(name = "to_date", nullable = true)
	private Date toDate;
	@Column(name = "operator", nullable = true)
	private String operator;
	@Column(name = "organization", nullable = true)
	private String organization;
	@Column(name = "report_date", nullable = true)
	private Date reportDate;
	@Column(name = "system_id", nullable = false)
	private String systemId;
	@Column(name = "groupid", nullable = false)
	private long groupId;
	@Column(name = "companyid", nullable = false)
	private long companyId;
	@Column(name = "userid", nullable = false)
	private long userId;
	@Column(name = "retrieved_by_name", nullable = false)
	private String retrievedByName;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTransCount() {
		return transCount;
	}

	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
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

	public String getRetrievedByName() {
		return retrievedByName;
	}

	public void setRetrievedByName(String retrievedByName) {
		this.retrievedByName = retrievedByName;
	}

	

}
