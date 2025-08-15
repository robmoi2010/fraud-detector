package com.goglotek.frauddetector.datastoreservice.model;

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
@Table(name = "files")
public class Files implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 251569524834023530L;
	@Id
	@Column(name = "file_id")
	@SequenceGenerator(name = "uploads_sequence", sequenceName = "files_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uploads_sequence")
	private Long fileId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "filename", nullable = false)
	private String fileName;
	@Column(name = "transactions_count", nullable = false)
	private Integer transactionsCount;
	@Column(name = "processed", nullable = false)
	private Boolean processed;
	@Column(name = "date_processed", nullable = true)
	private Date dateProcessed;
	@Column(name = "group_account", nullable = false)
	private String groupAccount;
	@Column(name = "from_date", nullable = true)
	private Date fromDate;
	@Column(name = "to_date", nullable = true)
	private Date toDate;
	@Column(name = "retrieved_by_name", nullable = false)
	private String retrievedByName;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
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

	public Integer getTransactionsCount() {
		return transactionsCount;
	}

	public void setTransactionsCount(Integer transactionsCount) {
		this.transactionsCount = transactionsCount;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public String getGroupAccount() {
		return groupAccount;
	}

	public void setGroupAccount(String groupAccount) {
		this.groupAccount = groupAccount;
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

	public String getRetrievedByName() {
		return retrievedByName;
	}

	public void setRetrievedByName(String retrievedByName) {
		this.retrievedByName = retrievedByName;
	}
}
