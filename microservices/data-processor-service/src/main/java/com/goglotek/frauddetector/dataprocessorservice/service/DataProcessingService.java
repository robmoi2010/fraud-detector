package com.goglotek.frauddetector.dataprocessorservice.service;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;

import java.util.List;

public interface DataProcessingService {
    void processData(List<FileDto> event);
}
