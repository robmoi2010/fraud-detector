package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.exception.MpesaReconciliationsNotFoundException;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconciliations;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaReconciliationsService;

@RequestMapping("reconciliations")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class MpesaReconciliationsController {
	@Autowired
	MpesaReconciliationsService mpesaReconciliationsService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MpesaReconciliations> mpesaReconciliations(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order) {
		List<MpesaReconciliations> recons = mpesaReconciliationsService.findAllPaged(page, limit, order);
		return recons;
	}

	@RequestMapping(value = "/{reconciliation_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaReconciliations mpesaReconciliations(
			@PathVariable(value = "reconciliation_id", required = true) Long reconId) {
		return mpesaReconciliationsService.findById(reconId)
				.orElseThrow(() -> new MpesaReconciliationsNotFoundException(
						"Mpesa reconciliations with id " + reconId + " not found"));
	}
}
