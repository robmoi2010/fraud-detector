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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.dto.GenericSuccessResponse;
import com.goglotek.frauddetector.datastoreservice.dto.ProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.exception.ProviderTransactionsNotFoundException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import com.goglotek.frauddetector.datastoreservice.service.ProviderTransactionsService;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
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

@RequestMapping("providertransactions")
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'MACHINE_USER')")
public class ProviderTransactionsController {

  @Autowired
  ProviderTransactionsService providerTransactionsService;

  @Autowired
  FilesService filesService;

  @Autowired
  Cryptography cryptography;

  @Autowired
  Config config;

  @Autowired
  ObjectMapper objectMapper;

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody ProviderTransactionsDto transactions(
      @RequestParam(name = "page", required = true) Integer page,
      @RequestParam(name = "limit", required = true) Integer limit,
      @RequestParam(name = "order_by", required = true) String order,
      @RequestParam(name = "direction", required = true) String direction) {
    ProviderTransactionsDto dto = new ProviderTransactionsDto();
    dto.setCount(providerTransactionsService.countAll());
    dto.setTransactions(providerTransactionsService.findAllPaged(page, limit, order, direction));
    return dto;
  }

  @RequestMapping(value = "/{transaction_id}", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody ProviderTransactions transaction(
      @PathVariable(value = "transaction_id", required = true) Long transId) {
    return providerTransactionsService.findById(transId).orElseThrow(
        () -> new ProviderTransactionsNotFoundException(
            "transaction with id " + transId + " not found"));
  }

  @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody ProviderTransactionsDto filteredTransactions(
      @RequestParam(name = "page", required = true) Integer page,
      @RequestParam(name = "limit", required = true) Integer limit,
      @RequestParam(name = "order_by", required = true) String order,
      @RequestParam(name = "direction", required = true) String direction,
      @RequestBody List<FilterModel> filterModel) {
    ProviderTransactionsDto dto = new ProviderTransactionsDto();
    dto.setCount(providerTransactionsService.countAllFilteredPaged(filterModel));
    dto.setTransactions(
        providerTransactionsService.findAllFilteredPaged(page, limit, order, direction,
            filterModel));
    return dto;
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<ProviderTransactions> search(@RequestBody String keyword) {
    return providerTransactionsService.search(keyword.trim().toLowerCase());
  }

  @RequestMapping(value = "/create/{file_global_id}", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody GenericSuccessResponse createTransactions(
      @PathVariable(value = "file_global_id", required = true) String fileGlobalId,
      @RequestBody String encryptedTransactions) throws GoglotekException {
    Files file = filesService.getFileByGlobalId(fileGlobalId);

    byte[] decrypted;

    try {
      decrypted = cryptography.decrypt(Base64.getDecoder().decode(encryptedTransactions.getBytes()),
          config.getEncryptionKey(), config.getEncryptionInitVector());
    } catch (GoglotekException e) {
      throw new GoglotekException(e,
          "Decryption error: Ensure data is encrypted with a private key known by the server");
    }

    List<CreateProviderTransactionsDto> transactionsDtoList;

    try {
      transactionsDtoList = objectMapper.readValue(decrypted,
          new TypeReference<List<CreateProviderTransactionsDto>>() {
          });
    } catch (IOException e) {
      throw new GoglotekException(e,
          "Error in json string. Ensure the payload is a valid json or of expected format.");
    }
    List<ProviderTransactions> transactions = providerTransactionsService.createAll(
        transactionsDtoList, file);
    GenericSuccessResponse response = new GenericSuccessResponse();
    response.setMessage(
        "success, " + transactions.size() + " for file " + file.getFileName() + " stored");
    response.setStatusCode(200);
    response.setSuccess(true);
    response.setServerTimestamp(new Date());
    return response;

  }

  @RequestMapping(value = "/globalid/{file_global_id}", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<ProviderTransactions> getAllByFileId(
      @PathVariable(value = "file_global_id", required = true) String fileId)
      throws GoglotekException {
    return providerTransactionsService.findByFileGlobalId(fileId);
  }
}
