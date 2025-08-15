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
@Table(name = "mpesa_fetch_settings")
public class MpesaFetchSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5703995793557235364L;
	@Id
	@Column(name = "settings_id")
	@SequenceGenerator(name = "mpesa_fetch_settings_sequence", sequenceName = "mpesa_fetch_settings_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mpesa_fetch_settings_sequence")
	private long settingsId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "active", nullable = false)
	private boolean active;
	@Column(name = "min_recon_date", nullable = false)
	private Date minReconDate;

	public long getSettingsId() {
		return settingsId;
	}

	public void setSettingsId(long settingsId) {
		this.settingsId = settingsId;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getMinReconDate() {
		return minReconDate;
	}

	public void setMinReconDate(Date minReconDate) {
		this.minReconDate = minReconDate;
	}

}
