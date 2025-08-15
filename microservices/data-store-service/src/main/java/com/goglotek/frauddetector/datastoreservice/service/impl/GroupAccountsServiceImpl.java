package com.goglotek.frauddetector.datastoreservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.GroupAccounts;
import com.goglotek.frauddetector.datastoreservice.repository.GroupAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.service.GroupAccountsService;

@Service
public class GroupAccountsServiceImpl implements GroupAccountsService {
	@Autowired
	GroupAccountsRepository groupAccountsRepository;

	@Override
	public GroupAccounts findByAccount(String account) {
		return groupAccountsRepository.findByAccount(account);
	}

	private GroupAccounts createFilterObject(List<FilterModel> filterModel) {
		GroupAccounts accounts = new GroupAccounts();
		for (FilterModel m : filterModel) {
			String filterColumn = m.getColumnField();
			String value = m.getValue();
			switch (filterColumn.toLowerCase()) {
			case "active":
				accounts.setActive(Boolean.parseBoolean(value));
				break;
			case "account":
				accounts.setAccount(value);
				break;
			}
		}
		return accounts;
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
		Example<GroupAccounts> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return groupAccountsRepository.count(example);
	}

	@Override
	public List<GroupAccounts> findAllFilteredPaged(Integer page, Integer limit, String order, String direction,
			List<FilterModel> filterModel) {
		Example<GroupAccounts> example = Example.of(createFilterObject(filterModel),
				createFilterMatcher(filterModel));
		return groupAccountsRepository
				.findAll(example, PageRequest.of(page, limit,
						direction.equalsIgnoreCase("desc") ? Sort.by(order).descending() : Sort.by(order).ascending()))
				.getContent();
	}

	@Override
	public List<GroupAccounts> findAll() {
		return groupAccountsRepository.findAll();
	}
}
