package com.goglotek.frauddetector.datastoreservice.model;

import java.util.List;

public class GenericResponse {
	private int count;
	private List<?> rows;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
