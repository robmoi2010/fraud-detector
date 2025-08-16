package com.goglotek.frauddetector.dataextractionservice.schema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schema {
    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("has_headers")
    private boolean hasHeaders;

    private Column[] columns;

    @JsonProperty("date_format")
    private String dateFormat;


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean hasHeaders() {
        return hasHeaders;
    }

    public void setHasHeaders(boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    public Column[] getColumns() {
        return columns;
    }

    public void setColumns(Column[] columns) {
        this.columns = columns;
    }
}
