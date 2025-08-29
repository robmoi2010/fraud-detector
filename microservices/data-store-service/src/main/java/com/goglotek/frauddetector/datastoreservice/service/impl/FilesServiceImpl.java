/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.datastoreservice.service.impl;

import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.exception.FileNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.repository.FilesRepository;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FilesServiceImpl implements FilesService {

  Logger logger = LogManager.getLogger(FilesServiceImpl.class);
  @Autowired
  FilesRepository filesRepository;

  @Override
  public Optional<Files> findById(long fileId) {
    return filesRepository.findById(fileId);
  }

  @Override
  public List<Files> findAllPaged(int page, int limit, String order, String direction) {
    return filesRepository
        .findAll(PageRequest.of(page, limit,
            direction.equalsIgnoreCase("desc") ? Sort.by(order).descending()
                : Sort.by(order).ascending()))
        .getContent();
  }

  @Override
  public long countAll() {
    return filesRepository.count();
  }

  @Override
  public Files findByFileName(String name) {
    // return FilesRepository.findByFilename(name);
    return null;
  }

  @Override
  public List<Files> find(String text) {
    return filesRepository.search(text, text);

  }

  private Files createFilterObject(String filterColumn, String value) {
    Files file = new Files();
    switch (filterColumn.toLowerCase()) {
      case "filename":
        file.setFileName(value);
        break;
      case "processed":
        file.setProcessed(Boolean.parseBoolean(value));
        break;
      case "account":
        file.setGroupAccount(value);
        break;
    }
    return file;
  }

  private ExampleMatcher createFilterMatcher(String operatorValue) {
    ExampleMatcher matcher = null;
    switch (operatorValue.toLowerCase()) {
      case "contains": {
        matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnoreNullValues();
        break;
      }
      case "equals": {
        matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.EXACT).withIgnoreCase()
            .withIgnoreNullValues();
      }
      case "startswith": {
        matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.STARTING)
            .withIgnoreCase()
            .withIgnoreNullValues();
        break;
      }
      case "endswith": {
        matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.ENDING).withIgnoreCase()
            .withIgnoreNullValues();
        break;
      }
      default:
        matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.EXACT).withIgnoreCase()
            .withIgnoreNullValues();
    }
    return matcher;
  }

  @Override
  public long countAllFilteredPaged(String filterColumn, String operatorValue, String value) {
    Example<Files> example = Example.of(createFilterObject(filterColumn, value),
        createFilterMatcher(operatorValue));
    return filesRepository.count(example);
  }

  @Override
  public List<Files> findAllFilteredPaged(Integer page, Integer limit, String order,
      String direction,
      String filterColumn, String operatorValue, String value) {
    Example<Files> example = Example.of(createFilterObject(filterColumn, value),
        createFilterMatcher(operatorValue));
    return filesRepository
        .findAll(example, PageRequest.of(page, limit,
            direction.equalsIgnoreCase("desc") ? Sort.by(order).descending()
                : Sort.by(order).ascending()))
        .getContent();
  }

  @Override
  public Files create(Files file) {
    return filesRepository.save(file);
  }

  @Override
  public Files createFile(CreateFileDto fileDto) {
    Files file = new Files();
    file.setGroupAccount(fileDto.getGroupAccount());
    file.setFileName(fileDto.getFileName());
    file.setProcessed(false);
    file.setCreatedDate(new Date());
    file.setTransactionsCount(fileDto.getTotalTransactions());
    file.setGlobalId(fileDto.getFileId());
    file.setFromDate(fileDto.getFromDate());
    file.setToDate(fileDto.getToDate());
    file.setModifiedDate(new Date());
    file.setRetrievedByName("DataExtractionService");
    return create(file);
  }

  @Override
  public Files getFileByGlobalId(String globalId) throws GoglotekException {
    return filesRepository.findFirstByGlobalId(globalId).orElseThrow(
        () -> new FileNotFoundException("File with global id " + globalId + " not found..."));
  }

  @Override
  public void save(Files file) {
    filesRepository.save(file);
  }

}
