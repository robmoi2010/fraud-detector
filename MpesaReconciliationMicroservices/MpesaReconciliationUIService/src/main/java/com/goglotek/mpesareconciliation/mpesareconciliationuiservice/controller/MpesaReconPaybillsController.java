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
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaReconPaybillsDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaReconPaybills;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaReconPaybillsService;

@RestController
@RequestMapping("paybills")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class MpesaReconPaybillsController {
	@Autowired
	private MpesaReconPaybillsService mpesaReconPaybillsService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MpesaReconPaybills> paybills() {
		return mpesaReconPaybillsService.findAll();
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody MpesaReconPaybillsDto filteredPaybills(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestBody List<FilterModel> filterModel) {
		MpesaReconPaybillsDto dto = new MpesaReconPaybillsDto();
		dto.setCount(mpesaReconPaybillsService.countAllFilteredPaged(filterModel));
		dto.setMpesaReconPaybills(
				mpesaReconPaybillsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
		return dto;
	}
}
