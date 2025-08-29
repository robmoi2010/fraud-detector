package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;

public interface FilesService {

    public Files findByFileName(String name);

    public List<Files> findAllPaged(int page, int limit, String order, String direction);

    public Optional<Files> findById(long fileId);

    public List<Files> find(String text);

    public long countAll();

    public long countAllFilteredPaged(String filterColumn, String operatorValue, String value);

    public List<Files> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
                                            String filterColumn, String operatorValue, String value);

    public Files create(Files file);

    public Files createFile(CreateFileDto fileDto);

    public Files getFileByGlobalId(String globalId) throws GoglotekException;

    void save(Files file);
}