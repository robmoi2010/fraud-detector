package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.FilterModel;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaFetchedTransDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.exception.MpesaFetchedTransNotFoundException;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFetchedTrans;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaFetchedTransService;

@RequestMapping("transactions")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class MpesaFetchedTransController {
	@Autowired
	MpesaFetchedTransService mpesaFetchedTransService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaFetchedTransDto transactions(@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction) {
		MpesaFetchedTransDto dto = new MpesaFetchedTransDto();
		dto.setCount(mpesaFetchedTransService.countAll());
		dto.setMpesaTransactions(mpesaFetchedTransService.findAllPaged(page, limit, order, direction));
		return dto;
	}

	@RequestMapping(value = "/{transaction_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaFetchedTrans mpesaFetchedTrans(
			@PathVariable(value = "transaction_id", required = true) Long transId) {
		return mpesaFetchedTransService.findById(transId).orElseThrow(
				() -> new MpesaFetchedTransNotFoundException("Mpesa transaction with id " + transId + " not found"));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody MpesaFetchedTransDto filteredTransactions(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestBody List<FilterModel> filterModel) {
		MpesaFetchedTransDto dto = new MpesaFetchedTransDto();
		dto.setCount(mpesaFetchedTransService.countAllFilteredPaged(filterModel));
		dto.setMpesaTransactions(
				mpesaFetchedTransService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MpesaFetchedTrans> search(@RequestBody String keyword) {
		return mpesaFetchedTransService.search(keyword.trim().toLowerCase());
	}
}
