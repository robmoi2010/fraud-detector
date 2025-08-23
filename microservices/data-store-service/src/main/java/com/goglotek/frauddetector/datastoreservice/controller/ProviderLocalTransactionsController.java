package com.goglotek.frauddetector.datastoreservice.controller;

import java.util.List;

import com.goglotek.frauddetector.datastoreservice.model.ProviderLocalTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.ProviderLocalTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.exception.ProviderLocalTransactionsNotFoundException;
import com.goglotek.frauddetector.datastoreservice.service.ProviderLocalTransactionsService;

@RequestMapping("providerlocaltransactions")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class ProviderLocalTransactionsController {
	@Autowired
	ProviderLocalTransactionsService providerLocalTransactionsService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ProviderLocalTransactions> providerLocalTransactions(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction) {
		return providerLocalTransactionsService.findAllPaged(page, limit, order,
				direction);
	}

	@RequestMapping(value = "/{provider_local_transaction_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ProviderLocalTransactions providerLocalTransactions(
			@PathVariable(value = "provider_local_transaction_id", required = true) Long transactionId) {
		return providerLocalTransactionsService.findById(transactionId)
				.orElseThrow(() -> new ProviderLocalTransactionsNotFoundException(
						"Mpesa reconciliation transaction with id " + transactionId + " is not found"));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ProviderLocalTransactionsDto filteredTransactions(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestBody List<FilterModel> filterModel) {
		ProviderLocalTransactionsDto dto = new ProviderLocalTransactionsDto();
		dto.setCount(providerLocalTransactionsService.countAllFilteredPaged(filterModel));
		dto.setProviderLocalTransactions(
				providerLocalTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ProviderLocalTransactions> search(@RequestBody String keyword) {
		return providerLocalTransactionsService.search(keyword.trim().toLowerCase());
	}
}
