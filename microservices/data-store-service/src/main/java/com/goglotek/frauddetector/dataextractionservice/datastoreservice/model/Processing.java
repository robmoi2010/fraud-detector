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
@Table(name = "processing")
public class Processing implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5344912536711422899L;
	@Id
	@Column(name = "processing")
	@SequenceGenerator(name = "processing_sequence", sequenceName = "processing_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processing_sequence")
	private long reconciliationId;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "modified_date")
	private Date modifiedDate;
	@Column(name = "total_discrepancy")
	private int totalDiscrepancy;
	@Column(name = "group_id")
	private long groupId;
	@Column(name = "company_id")
	private long companyId;
	@Column(name = "user_id")
	private long userId;
	@Column(name = "user_name")
	private String username;
	@Column(name = "file_id")
	private long fileId;

	public long getReconciliationId() {
		return reconciliationId;
	}

	public void setReconciliationId(long reconciliationId) {
		this.reconciliationId = reconciliationId;
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

	public int getTotalDiscrepancy() {
		return totalDiscrepancy;
	}

	public void setTotalDiscrepancy(int totalDiscrepancy) {
		this.totalDiscrepancy = totalDiscrepancy;
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

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
}