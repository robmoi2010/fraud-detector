package com.goglotek.frauddetector.dataextractionservice.datastoreservice.controller;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.GroupAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.dto.GroupAccountsDto;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.GroupAccountsService;

@RestController
@RequestMapping("accounts")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class GroupAccountsController {
	@Autowired
	private GroupAccountsService groupAccountsService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<GroupAccounts> accounts() {
		return groupAccountsService.findAll();
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GroupAccountsDto filteredAccounts(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestBody List<FilterModel> filterModel) {
		GroupAccountsDto dto = new GroupAccountsDto();
		dto.setCount(groupAccountsService.countAllFilteredPaged(filterModel));
		dto.setGroupAccounts(
				groupAccountsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}
}
