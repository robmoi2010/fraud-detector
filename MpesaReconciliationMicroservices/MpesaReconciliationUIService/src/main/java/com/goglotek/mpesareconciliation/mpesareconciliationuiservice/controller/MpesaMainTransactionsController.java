package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaMainTransactionsDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaMainTransactionsService;

@RequestMapping("maintransactions")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class MpesaMainTransactionsController {
	@Autowired
	private MpesaMainTransactionsService mpesaMainTransactionsService;

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody MpesaMainTransactionsDto filteredMainTransactions(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestBody List<FilterModel> filterModel) {
		MpesaMainTransactionsDto dto = new MpesaMainTransactionsDto();
		dto.setCount(mpesaMainTransactionsService.countAllFilteredPaged(filterModel));
		dto.setMpesaMainTransactions(
				mpesaMainTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}

}
