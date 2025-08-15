package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service;

import java.util.List;
import java.util.Optional;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFiles;

public interface MpesaFilesService {

	MpesaFiles findByFileName(String name);

	List<MpesaFiles> findAllPaged(int page, int limit, String order, String direction);

	Optional<MpesaFiles> findById(long fileId);

	List<MpesaFiles> find(String text);

	long countAll();

	long countAllFilteredPaged(String filterColumn, String operatorValue, String value);

	List<MpesaFiles> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			String filterColumn, String operatorValue, String value);

}