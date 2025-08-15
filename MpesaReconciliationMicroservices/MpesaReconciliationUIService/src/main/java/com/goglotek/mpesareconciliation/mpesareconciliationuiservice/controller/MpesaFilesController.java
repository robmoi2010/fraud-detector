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

import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaFetchedTransDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.dto.MpesaFilesDto;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.exception.MpesaFileNotFoundException;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model.MpesaFiles;
import com.goglotek.mpesareconciliation.mpesareconciliationuiservice.service.MpesaFilesService;

@RestController
@RequestMapping("/files")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class MpesaFilesController {
	@Autowired
	MpesaFilesService mpesaFilesService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaFilesDto files(@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction) {
		MpesaFilesDto dto = new MpesaFilesDto();
		dto.setCount(mpesaFilesService.countAll());
		dto.setMpesaFiles(mpesaFilesService.findAllPaged(page, limit, order, direction));
		return dto;
	}

	@RequestMapping(value = "/{file_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaFiles mpesaFile(@PathVariable(value = "file_id", required = false) Long fileId) {
		return mpesaFilesService.findById(fileId)
				.orElseThrow(() -> new MpesaFileNotFoundException("Mpesa file with id " + fileId + " not found"));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MpesaFilesDto filteredMpesaFiles(@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order,
			@RequestParam(name = "direction", required = true) String direction,
			@RequestParam(name = "filter_column", required = true) String filterColumn,
			@RequestParam(name = "operator_value", required = true) String operatorValue,
			@RequestParam(name = "value", required = true) String value) {
		MpesaFilesDto dto = new MpesaFilesDto();
		dto.setCount(mpesaFilesService.countAllFilteredPaged(filterColumn, operatorValue, value));
		dto.setMpesaFiles(mpesaFilesService.findAllFilteredPaged(page, limit, order, direction, filterColumn,
				operatorValue, value));
		return dto;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MpesaFiles> search(@RequestBody String text) {
		return mpesaFilesService.find(text.trim().toLowerCase());
	}

}
