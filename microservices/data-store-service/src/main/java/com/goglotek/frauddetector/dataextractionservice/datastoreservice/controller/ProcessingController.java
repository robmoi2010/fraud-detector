package com.goglotek.frauddetector.dataextractionservice.datastoreservice.controller;

import java.util.List;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.model.Processing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.dataextractionservice.datastoreservice.exception.ProcessingNotFoundException;
import com.goglotek.frauddetector.dataextractionservice.datastoreservice.service.ProcessingService;

@RequestMapping("processing")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class ProcessingController {
	@Autowired
	ProcessingService processingService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Processing> processings(
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "limit", required = true) Integer limit,
			@RequestParam(name = "order_by", required = true) String order) {
        return processingService.findAllPaged(page, limit, order);
	}

	@RequestMapping(value = "/{processing_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Processing processings(
			@PathVariable(value = "processing_id", required = true) Long processingId) {
		return processingService.findById(processingId)
				.orElseThrow(() -> new ProcessingNotFoundException(
						"Mpesa reconciliations with id " + processingId + " not found"));
	}
}
