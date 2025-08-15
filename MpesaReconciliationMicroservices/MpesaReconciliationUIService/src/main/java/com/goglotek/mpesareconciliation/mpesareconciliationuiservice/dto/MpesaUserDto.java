package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaUser;

public class MpesaUserDto {
	private long count;
	private List<MpesaUser> mpesaUser;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaUser> getMpesaUser() {
		return mpesaUser;
	}

	public void setMpesaUser(List<MpesaUser> mpesaUser) {
		this.mpesaUser = mpesaUser;
	}

}
