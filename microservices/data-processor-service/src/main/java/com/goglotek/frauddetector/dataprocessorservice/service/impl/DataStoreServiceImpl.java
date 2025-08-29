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

package com.goglotek.frauddetector.dataprocessorservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataprocessorservice.configuration.Config;
import com.goglotek.frauddetector.dataprocessorservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.LocalTransactionsMixin;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProviderTransactionsMixin;
import com.goglotek.frauddetector.dataprocessorservice.dto.SuccessResponse;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import com.goglotek.frauddetector.dataprocessorservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataprocessorservice.rest.RestClient;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataStoreServiceImpl implements DataStoreService {

  @Autowired
  RestClient restClient;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private Config config;

  @Autowired
  private Cryptography cryptography;

  @Override
  public List<Transaction> getProviderTransactions(FileDto file) throws Exception {
    //get transactions from data store
    String response = restClient.get(
        config.getBaseUrl() + config.getGetProviderTransactionsEndpoint() + "/" + file.getFileId());
    mapper.addMixIn(Transaction.class, ProviderTransactionsMixin.class);
    return mapper.readValue(response, new TypeReference<List<Transaction>>() {
    });
  }

  @Override
  public List<Transaction> getLocalTransactions(FileDto file) throws Exception {
    //get transactions from data store
    SimpleDateFormat format = new SimpleDateFormat(config.getInternalTimestampFormat());
    String from = format.format(file.getFromDate());
    String to = format.format(file.getToDate());
    String response = restClient.get(
        config.getBaseUrl() + config.getGetLocalTransactionsEndpoint() + "?from=" + from + "&to="
            + to);
    mapper.addMixIn(Transaction.class, LocalTransactionsMixin.class);
    return mapper.readValue(response, new TypeReference<List<Transaction>>() {
    });
  }

  @Override
  public void storeProcessedTransactionData(ProcessedTransactionDto processed) throws Exception {
    String payload = new ObjectMapper().writeValueAsString(processed);
    byte[] encrypted = cryptography.encrypt(payload.getBytes(), config.getEncryptionKey(),
        config.getInitVector());
    String response = restClient.post(config.getBaseUrl() + config.getProcessedTransEndpoint(),
        Base64.getEncoder().encodeToString(encrypted));
    SuccessResponse resp = mapper.readValue(response, SuccessResponse.class);
    if (!resp.isSuccess()) {
      throw new GoglotekException("Error sending processed data: " + response);
    }
  }
}
