package com.goglotek.frauddetector.datastoreservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.datastoreservice.exception.ErrorResponse;

@RestController
@RequestMapping("servicetest")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class ServiceTesterController {
	@Value("${server.port}")
	private int serverPort;

	@RequestMapping(value = "/testloadbalancer", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ErrorResponse testLoadBalancing() {
		ErrorResponse resp = new ErrorResponse();
		resp.setCode("" + serverPort);
		resp.setMessage("Service on port " + serverPort + " handling the request");
		resp.setStatus(404);
		return resp;
	}
}
