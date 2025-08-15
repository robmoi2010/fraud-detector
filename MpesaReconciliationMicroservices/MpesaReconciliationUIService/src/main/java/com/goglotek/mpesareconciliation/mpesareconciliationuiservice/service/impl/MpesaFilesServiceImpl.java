package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFiles;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaFilesRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaFilesService;

@Service
public class MpesaFilesServiceImpl implements MpesaFilesService {
	Logger logger = LogManager.getLogger(MpesaFilesServiceImpl.class);
	@Autowired
	MpesaFilesRepository mpesaFilesRepository;

	@Override
	public Optional<MpesaFiles> findById(long fileId) {
		return mpesaFilesRepository.findById(fileId);
	}

	@Override
	public List<MpesaFiles> findAllPaged(int page, int limit, String order, String direction) {
		return mpesaFilesRepository
				.findAll(PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public long countAll() {
		return mpesaFilesRepository.count();
	}

	@Override
	public MpesaFiles findByFileName(String name) {
		// return mpesaFilesRepository.findByFilename(name);
		return null;
	}

	@Override
	public List<MpesaFiles> find(String text) {
		return mpesaFilesRepository.search(text, text, text, text, text);

	}

	private MpesaFiles createFilterObject(String filterColumn, String value) {
		MpesaFiles file = new MpesaFiles();
		switch (filterColumn.toLowerCase()) {
		case "filename":
			file.setFileName(value);
			break;
		case "processed":
			file.setProcessed(Boolean.parseBoolean(value));
			break;
		case "accountholder":
			file.setAccountHolder(value);
			break;
		case "shortcode":
			file.setShortcode(value);
			break;
		case "account":
			file.setAccount(value);
			break;
		case "operator":
			file.setOperator(value);
			break;
		case "organization":
			file.setOrganization(value);
			break;
		}
		return file;
	}

	private ExampleMatcher createFilterMatcher(String operatorValue) {
		ExampleMatcher matcher = null;
		switch (operatorValue.toLowerCase()) {
		case "contains": {
			matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase()
					.withIgnoreNullValues();
			break;
		}
		case "equals": {
			matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.EXACT).withIgnoreCase()
					.withIgnoreNullValues();
		}
		case "startswith": {
			matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.STARTING).withIgnoreCase()
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
		Example<MpesaFiles> example = Example.of(createFilterObject(filterColumn, value),
				createFilterMatcher(operatorValue));
		return mpesaFilesRepository.count(example);
	}

	@Override
	public List<MpesaFiles> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			String filterColumn, String operatorValue, String value) {
		Example<MpesaFiles> example = Example.of(createFilterObject(filterColumn, value),
				createFilterMatcher(operatorValue));
		return mpesaFilesRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

}
