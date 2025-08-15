package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto;

public class FilterModel {
	private String columnField;
	private String operatorValue;
	private String value;

	public String getColumnField() {
		return columnField;
	}

	public void setColumnField(String columnField) {
		this.columnField = columnField;
	}

	public String getOperatorValue() {
		return operatorValue;
	}

	public void setOperatorValue(String operatorValue) {
		this.operatorValue = operatorValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
