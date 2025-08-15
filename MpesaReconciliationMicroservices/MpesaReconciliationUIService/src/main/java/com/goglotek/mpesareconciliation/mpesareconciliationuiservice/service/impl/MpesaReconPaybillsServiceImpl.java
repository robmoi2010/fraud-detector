package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconPaybills;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaReconPaybillsRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaReconPaybillsService;

@Service
public class MpesaReconPaybillsServiceImpl implements MpesaReconPaybillsService {
	@Autowired
	MpesaReconPaybillsRepository mpesaReconPaybillsRepository;

	@Override
	public MpesaReconPaybills findByPaybill(String paybill) {
		return mpesaReconPaybillsRepository.findByPaybill(paybill);
	}

	private MpesaReconPaybills createFilterObject(List<FilterModel> filterModel) {
		MpesaReconPaybills paybills = new MpesaReconPaybills();
		for (FilterModel m : filterModel) {
			String filterColumn = m.getColumnField();
			String value = m.getValue();
			switch (filterColumn.toLowerCase()) {
			case "active":
				paybills.setActive(Boolean.parseBoolean(value));
				break;
			case "paybill":
				paybills.setPaybill(value);
				break;
			}
		}
		return paybills;
	}

	private ExampleMatcher createFilterMatcher(List<FilterModel> filterModel) {
		ExampleMatcher matcher = ExampleMatcher.matchingAll();
		for (FilterModel m : filterModel) {
			switch (m.getOperatorValue().toLowerCase()) {
			case "contains": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "equals": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "startswith": {
				matcher = matcher
						.withMatcher(m.getColumnField(),
								ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			case "endswith": {
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
			default:
				matcher = matcher
						.withMatcher(m.getColumnField(), ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
						.withIgnoreNullValues();
				break;
			}
		}
		matcher = matcher.withIgnoreNullValues();
		return matcher;
	}

	@Override
	public long countAllFilteredPaged(List<FilterModel> filterModel) {
		Example<MpesaReconPaybills> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaReconPaybillsRepository.count(example);
	}

	@Override
	public List<MpesaReconPaybills> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel) {
		Example<MpesaReconPaybills> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaReconPaybillsRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public List<MpesaReconPaybills> findAll() {
		return mpesaReconPaybillsRepository.findAll();
	}
}
