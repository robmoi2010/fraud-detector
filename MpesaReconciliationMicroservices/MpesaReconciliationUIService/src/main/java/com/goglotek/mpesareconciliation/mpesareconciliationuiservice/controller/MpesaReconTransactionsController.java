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
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaReconTransactionsDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.exception.MpesaReconTransactionsNotFoundException;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconTransactions;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaReconTransactionsService;

@RequestMapping("reconciledtransactions")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class MpesaReconTransactionsController {
	@Autowired
	MpesaReconTransactionsService mpesaReconTransactionsService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MpesaReconTransactions> mpesaReconTransactions(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction) {
		List<MpesaReconTransactions> reconsTrans = mpesaReconTransactionsService.findAllPaged(page, limit, order,
				direction);
		return reconsTrans;
	}

	@RequestMapping(value = "/{recon_trans_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaReconTransactions mpesaReconTransaction(
			@PathVariable(value = "recon_trans_id", required = true) Long reconTransId) {
		return mpesaReconTransactionsService.findById(reconTransId)
				.orElseThrow(() -> new MpesaReconTransactionsNotFoundException(
						"Mpesa reconciliation transaction with id " + reconTransId + " is not found"));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody MpesaReconTransactionsDto filteredReconTransactions(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestBody List<FilterModel> filterModel) {
		MpesaReconTransactionsDto dto = new MpesaReconTransactionsDto();
		dto.setCount(mpesaReconTransactionsService.countAllFilteredPaged(filterModel));
		dto.setMpesaReconTransactions(
				mpesaReconTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MpesaReconTransactions> search(@RequestBody String keyword) {
		return mpesaReconTransactionsService.search(keyword.trim().toLowerCase());
	}
}
