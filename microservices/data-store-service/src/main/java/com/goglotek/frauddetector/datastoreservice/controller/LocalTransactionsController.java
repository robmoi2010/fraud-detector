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

import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.LocalTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.model.LocalTransactions;
import com.goglotek.frauddetector.datastoreservice.service.LocalTransactionsService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("localtransactions")
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'MACHINE_USER')")
public class LocalTransactionsController {

  @Autowired
  private Config config;

  @Autowired
  private LocalTransactionsService localTransactionsService;

  @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody LocalTransactionsDto filteredLocalTransactions(
      @RequestParam(name = "page", required = true) Integer page,
      @RequestParam(name = "limit", required = true) Integer limit,
      @RequestParam(name = "order_by", required = true) String order,
      @RequestParam(name = "direction", required = true) String direction,
      @RequestBody List<FilterModel> filterModel) {
    LocalTransactionsDto dto = new LocalTransactionsDto();
    dto.setCount(localTransactionsService.countAllFilteredPaged(filterModel));
    dto.setLocalTransactions(
        localTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
    return dto;
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LocalTransactions> getLocalTransactionsByPeriod(
      @RequestParam(value = "from") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date from,
      @RequestParam(value = "to") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date to

  ) {

    return localTransactionsService.findByTimePeriod(from, to);
  }

}
