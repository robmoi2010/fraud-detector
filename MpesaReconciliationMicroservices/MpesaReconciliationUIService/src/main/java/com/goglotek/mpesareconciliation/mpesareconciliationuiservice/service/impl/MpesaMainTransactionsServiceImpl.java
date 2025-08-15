package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaMainTransactions;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.repository.MpesaMainTransactionsRepository;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaMainTransactionsService;

@Service
public class MpesaMainTransactionsServiceImpl implements MpesaMainTransactionsService {
	@Autowired
	private MpesaMainTransactionsRepository mpesaMainTransactionsRepository;

	private MpesaMainTransactions createFilterObject(List<FilterModel> filterModel) {
		MpesaMainTransactions trans = new MpesaMainTransactions();
		for (FilterModel m : filterModel) {
			String filterColumn = m.getColumnField();
			String value = m.getValue();
			switch (filterColumn.toLowerCase()) {
			case "transtype":
				trans.setTransType(value);
				break;
			case "transid":
				trans.setTransId(value);
				break;
			case "transamount":
				trans.setTransAmount(Double.parseDouble(value));
				break;
			case "businessshortcode":
				trans.setBusinessShortcode(value);
				break;
			case "billrefnumber":
				trans.setBillRefNumber(value);
				break;
			case "invoicenumber":
				trans.setInvoiceNumber(value);
			case "orgaccountbalance":
				trans.setOrgAccountBalance(Double.parseDouble(value));
				break;
			case "msisdn":
				trans.setMsisdn(value);
				break;
			case "lastname":
				trans.setLastname(value);
				break;
			case "firstname":
				trans.setFirstname(value);
				break;
			case "middlename":
				trans.setMiddlename(value);
				break;
			case "isfromreconciliation":
				trans.setFromReconciliation(Boolean.parseBoolean(value));
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
		Example<MpesaMainTransactions> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaMainTransactionsRepository.count(example);
	}

	@Override
	public List<MpesaMainTransactions> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel) {
		Example<MpesaMainTransactions> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return mpesaMainTransactionsRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

}
