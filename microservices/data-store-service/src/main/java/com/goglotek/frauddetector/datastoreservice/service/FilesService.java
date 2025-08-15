package com.goglotek.frauddetector.datastoreservice.service;

import java.util.List;
import java.util.Optional;
import com.goglotek.frauddetector.datastoreservice.model.Files;

public interface FilesService {

	Files findByFileName(String name);

	List<Files> findAllPaged(int page, int limit, String order, String direction);

	Optional<Files> findById(long fileId);

	List<Files> find(String text);

	long countAll();

	long countAllFilteredPaged(String filterColumn, String operatorValue, String value);

	List<Files> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			String filterColumn, String operatorValue, String value);

}