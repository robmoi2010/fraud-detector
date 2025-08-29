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

package com.goglotek.frauddetector.dataextractionservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.cryptography.Cryptography;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.rest.RestClient;
import com.goglotek.frauddetector.dataextractionservice.service.DataStoreService;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataStoreServiceImpl implements DataStoreService {

  @Autowired
  private RestClient restClient;

  @Autowired
  private Config config;

  @Autowired
  private Cryptography cryptography;

  @Autowired
  ObjectMapper mapper;

  @Override
  public String storeFile(FileDto file) throws GoglotekException {
    try {
      String unencryptedTxt = mapper.writeValueAsString(file);
      //encrypt the data
      byte[] encrypted = cryptography.encrypt(unencryptedTxt.getBytes(), config.getEncryptionKey(),
          config.getEncryptionInitVector());
      return restClient.post(config.getBaseUrl() + "" + config.getPushFilesEndpoint(),
          Base64.getEncoder().encodeToString(encrypted));
    } catch (JsonProcessingException e) {
      throw new GoglotekException(e, "File parse to json failed:" + e.getMessage());
    } catch (Exception e) {
      throw new GoglotekException(e,
          "Error occurred while sending files to server:" + e.getMessage());
    }
  }

  //TODO: for paying clients, batch transactions if they are more than a certain threshold
  @Override
  public String storeTransactions(List<Transaction> transactions, String fileId)
      throws GoglotekException {
    try {
      String unencryptedTxt = mapper.writeValueAsString(transactions);
      //encrypt the data
      byte[] encrypted = cryptography.encrypt(unencryptedTxt.getBytes(), config.getEncryptionKey(),
          config.getEncryptionInitVector());
      return restClient.post(
          config.getBaseUrl() + "" + config.getPushTransactionsEndpoint() + "/" + fileId,
          Base64.getEncoder().encodeToString(encrypted));
    } catch (JsonProcessingException e) {
      throw new GoglotekException(e, "Transactions parse to json failed:" + e.getMessage());
    } catch (Exception e) {
      throw new GoglotekException(e,
          "Error occurred while sending files to server:" + e.getMessage());
    }
  }
}
