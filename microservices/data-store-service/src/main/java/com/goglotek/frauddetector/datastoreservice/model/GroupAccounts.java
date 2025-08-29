package com.goglotek.frauddetector.datastoreservice.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "group_accounts")
public class GroupAccounts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8372407013721706659L;
	@Id
	@Column(name = "group_account_id")
	@SequenceGenerator(name = "group_accounts_sequence", sequenceName = "group_accounts_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_accounts_sequence")
	private Long groupAccountId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "active", nullable = false)
	private Boolean active;
	@Column(name = "account", nullable = false)
	private String account;


	public Long getGroupAccountId() {
		return groupAccountId;
	}

	public void setGroupAccountId(Long groupAccountId) {
		this.groupAccountId = groupAccountId;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
