package com.goglotek.mpesareconciliation.handlerservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mpesa_paybill")
public class MpesaPaybill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -911086095058921981L;
	@Id
	@Column(name = "paybillid")
	@SequenceGenerator(name = "paybill_sequence", sequenceName = "mpesa_paybill_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paybill_sequence")
	private long paybillId;
	@Column(name = "paybillNo", nullable = false)
	private String paybillNo;
	@Column(name = "businessunit", nullable = false)
	private String businessUnit;
	@Column(name = "status", nullable = false)
	private String status;

	public long getPaybillId() {
		return paybillId;
	}

	public void setPaybillId(long paybillId) {
		this.paybillId = paybillId;
	}

	public String getPaybillNo() {
		return paybillNo;
	}

	public void setPaybillNo(String paybillNo) {
		this.paybillNo = paybillNo;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
