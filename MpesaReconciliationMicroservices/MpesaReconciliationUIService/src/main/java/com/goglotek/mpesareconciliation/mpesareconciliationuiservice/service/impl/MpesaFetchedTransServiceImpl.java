package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchedTrans;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaFetchedTransRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaFetchedTransService;

@Service
public class MpesaFetchedTransServiceImpl implements MpesaFetchedTransService {
	@Autowired
	MpesaFetchedTransRepository mpesaFetchedTransRepository;

	@Override
	public List<MpesaFetchedTrans> list() {
		return mpesaFetchedTransRepository.findAll();
	}

	private MpesaFetchedTrans createFilterObject(List<FilterModel> filterModel) {
		MpesaFetchedTrans trans = new MpesaFetchedTrans();
		for (FilterModel m : filterModel) {
			String filterColumn = m.getColumnField();
			String value = m.getValue();
			switch (filterColumn.toLowerCase()) {
			case "receiptno":
				trans.setReceiptNo(value);
				break;
			case "details":
				trans.setDetails(value);
				break;
			case "paidin":
				trans.setPaidIn(Double.parseDouble(value));
				break;
			case "fileid":
				trans.setFileId(Long.parseLong(value));
				break;
			case "reasontype":
				trans.setReasonType(value);
				break;
			case "transactionstatus":
				trans.setTransactionStatus(value);
			case "otherpartyinfo":
				trans.setOtherPartyInfo(value);
				break;
			case "accountno":
				trans.setAccountNo(value);
				break;
			case "msisdn":
				trans.setMsisdn(value);
				break;
			case "shortcode":
				trans.setShortcode(value);
				break;
			case "firstname":
				trans.setFirstname(value);
				break;
			case "middlename":
				trans.setMiddlename(value);
				break;
			case "lastname":
				trans.setLastname(value);
				break;
			}
		}
		return trans;
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
		Example<MpesaFetchedTrans> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaFetchedTransRepository.count(example);
	}

	@Override
	public List<MpesaFetchedTrans> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel) {
		Example<MpesaFetchedTrans> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaFetchedTransRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public List<MpesaFetchedTrans> findAllPaged(Integer page, Integer limit, String order, String direction) {
		return mpesaFetchedTransRepository
				.findAll(PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public Optional<MpesaFetchedTrans> findById(Long transId) {
		return mpesaFetchedTransRepository.findById(transId);
	}

	@Override
	public List<MpesaFetchedTrans> search(String keyword) {
		return mpesaFetchedTransRepository.search(keyword, keyword, keyword, keyword, keyword, keyword, keyword,
				keyword, keyword, keyword);
	}

	@Override
	public long countAll() {
		return mpesaFetchedTransRepository.count();
	}
}
