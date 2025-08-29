package com.goglotek.frauddetector.datastoreservice.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Settings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5703995793557235364L;
	@Id
	@Column(name = "settings_id")
	@SequenceGenerator(name = "settings_sequence", sequenceName = "settings_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_sequence")
	private long settingsId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "active", nullable = false)
	private boolean active;
	@Column(name = "minimum_processing_date", nullable = false)
	private Date minimumProcessingDate;

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

	public Date getMinimumProcessingDate() {
		return minimumProcessingDate;
	}

	public void setMinimumProcessingDate(Date minimumProcessingDate) {
		this.minimumProcessingDate = minimumProcessingDate;
	}
}
