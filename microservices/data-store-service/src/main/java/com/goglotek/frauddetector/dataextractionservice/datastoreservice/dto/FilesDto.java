package com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Files;

public class FilesDto {
    private long count;
    private List<Files> files;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

}
