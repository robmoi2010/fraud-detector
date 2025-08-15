package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaRole;

public class MpesaRoleDto {
	private long count;
	private List<MpesaRole> mpesaRole;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaRole> getMpesaRole() {
		return mpesaRole;
	}

	public void setMpesaRole(List<MpesaRole> mpesaRole) {
		this.mpesaRole = mpesaRole;
	}

}
