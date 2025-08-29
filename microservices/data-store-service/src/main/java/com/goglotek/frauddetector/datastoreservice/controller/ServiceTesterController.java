/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.datastoreservice.controller;

import com.goglotek.frauddetector.datastoreservice.exception.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
