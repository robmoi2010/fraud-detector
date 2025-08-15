package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconPaybills;

public class MpesaReconPaybillsDto {
	private long count;
	private List<MpesaReconPaybills> mpesaReconPaybills;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaReconPaybills> getMpesaReconPaybills() {
		return mpesaReconPaybills;
	}

	public void setMpesaReconPaybills(List<MpesaReconPaybills> mpesaReconPaybills) {
		this.mpesaReconPaybills = mpesaReconPaybills;
	}

}
