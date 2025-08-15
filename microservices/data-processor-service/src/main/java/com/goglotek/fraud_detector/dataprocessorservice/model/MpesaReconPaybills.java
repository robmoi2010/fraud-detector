package com.goglotek.fraud_detector.dataprocessorservice.model;

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
@Table(name = "mpesa_recon_paybills")
public class MpesaReconPaybills implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8372407013721706659L;
	@Id
	@Column(name = "recon_paybill_id")
	@SequenceGenerator(name = "recon_paybills_sequence", sequenceName = "mpesa_recon_paybills_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recon_paybills_sequence")
	private long reconPaybillId;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
	@Column(name = "active", nullable = false)
	private boolean active;
	@Column(name = "paybill", nullable = false)
	private String paybill;

	public long getReconPaybillId() {
		return reconPaybillId;
	}

	public void setReconPaybillId(long reconPaybillId) {
		this.reconPaybillId = reconPaybillId;
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

	public String getPaybill() {
		return paybill;
	}

	public void setPaybill(String paybill) {
		this.paybill = paybill;
	}

}
