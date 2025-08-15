package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

import java.util.List;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFiles;

public class MpesaFilesDto {
	private long count;
	private List<MpesaFiles> mpesaFiles;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MpesaFiles> getMpesaFiles() {
		return mpesaFiles;
	}

	public void setMpesaFiles(List<MpesaFiles> mpesaFiles) {
		this.mpesaFiles = mpesaFiles;
	}

}
