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

import com.goglotek.frauddetector.datastoreservice.exception.ProcessingNotFoundException;
import com.goglotek.frauddetector.datastoreservice.model.Processing;
import com.goglotek.frauddetector.datastoreservice.service.ProcessingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
