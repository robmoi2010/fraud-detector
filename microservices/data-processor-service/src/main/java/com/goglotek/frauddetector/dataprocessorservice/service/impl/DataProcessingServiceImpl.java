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

import com.goglotek.frauddetector.dataprocessorservice.configuration.Config;
import com.goglotek.frauddetector.dataprocessorservice.dto.DiscrepancyType;
import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import com.goglotek.frauddetector.dataprocessorservice.service.DataProcessingService;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//TODO: for paying clients, batch transaction processing for large transactions. NB: If a companies transactions are to large to process once then they should contract us for a custom proper implementation.
@Service
public class DataProcessingServiceImpl implements DataProcessingService {

  static Logger logger = LogManager.getLogger(DataProcessingServiceImpl.class);
  @Autowired
  private DataStoreService dataStoreService;

  @Autowired
  private Config config;

  @Override
  public void processData(List<FileDto> files) throws Exception {
    for (FileDto file : files) {
      try {
        logger.info("started processing transactions of file " + file.getFileName());
        //get local and provider transactions from server
        List<Transaction> providerTrans = dataStoreService.getProviderTransactions(file);
        List<Transaction> localTrans = dataStoreService.getLocalTransactions(file);

        //process transactions and get fraudulent and missing transactions
        Map<DiscrepancyType, List<Transaction>> fraudulentResults = getFraudulentTransactions(
            providerTrans, localTrans);
        Map<DiscrepancyType, List<Transaction>> missingResults = getMissingTransactions(
            providerTrans, localTrans);

        //send processed transactions to data store
        ProcessedTransactionDto processed = new ProcessedTransactionDto();
        processed.setFrom(file.getFromDate());
        processed.setTo(file.getToDate());
        processed.setDateProcessed(new Date());
        processed.setFileGlobalId(file.getFileId());
        processed.setFraudulentTransactions(fraudulentResults.get(DiscrepancyType.FRAUDULENT));
        processed.setMissingTransactions(missingResults.get(DiscrepancyType.MISSING));

        List<Transaction> invalidAmountTrans = fraudulentResults.get(
            DiscrepancyType.INVALID_AMOUNT);
        invalidAmountTrans.addAll(missingResults.get(DiscrepancyType.INVALID_AMOUNT));
        processed.setInvalidAmountTransactions(invalidAmountTrans);

        List<Transaction> invalidClientAccTrans = fraudulentResults.get(
            DiscrepancyType.INVALID_CLIENT_ID);
        invalidClientAccTrans.addAll(missingResults.get(DiscrepancyType.INVALID_CLIENT_ID));
        processed.setInvalidClientAccountTransactions(invalidClientAccTrans);

        List<Transaction> invalidTimestampTrans = fraudulentResults.get(
            DiscrepancyType.INVALID_TIMESTAMP);
        invalidTimestampTrans.addAll(missingResults.get(DiscrepancyType.INVALID_TIMESTAMP));
        processed.setInvalidTimestampTransactions(invalidTimestampTrans);

        //send processed data back to server
        dataStoreService.storeProcessedTransactionData(processed);
        logger.info("completed processing transactions of file " + file.getFileName());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  private Map<DiscrepancyType, List<Transaction>> getFraudulentTransactions(
      List<Transaction> provider, List<Transaction> local) {
    List<Transaction> fraudulentTrans = new ArrayList<>();
    List<Transaction> invalidAmountTrans = new ArrayList<>();
    List<Transaction> invalidClientIdTrans = new ArrayList<>();
    List<Transaction> invalidTimestampTrans = new ArrayList<>();
    for (Transaction loc : local) {
      boolean found = false;
      for (Transaction prov : provider) {
        if (loc.getId().equals(prov.getId())) {
          found = true;
          if (amountsNotEqual(loc.getAmount(), prov.getAmount())) {
            invalidAmountTrans.add(loc);
          }
          if (timestampNotEqual(loc.getTransactionTimestamp(), prov.getTransactionTimestamp())) {
            invalidTimestampTrans.add(loc);
          }
          if (clientIdNotEqual(loc.getClientAccount(), prov.getClientAccount())) {
            invalidClientIdTrans.add(loc);
          }
          break;
        }
      }
      if (!found) {
        fraudulentTrans.add(loc);
      }
    }
    Map<DiscrepancyType, List<Transaction>> results = new HashMap<>();
    results.put(DiscrepancyType.FRAUDULENT, fraudulentTrans);
    results.put(DiscrepancyType.INVALID_AMOUNT, invalidAmountTrans);
    results.put(DiscrepancyType.INVALID_CLIENT_ID, invalidClientIdTrans);
    results.put(DiscrepancyType.INVALID_TIMESTAMP, invalidTimestampTrans);
    return results;
  }

  private Map<DiscrepancyType, List<Transaction>> getMissingTransactions(List<Transaction> provider,
      List<Transaction> local) {
    List<Transaction> missingTrans = new ArrayList<>();
    List<Transaction> invalidAmountTrans = new ArrayList<>();
    List<Transaction> invalidClientIdTrans = new ArrayList<>();
    List<Transaction> invalidTimestampTrans = new ArrayList<>();
    for (Transaction prov : provider) {
      boolean found = false;
      for (Transaction loc : local) {
        if (prov.getId().equals(loc.getId())) {
          found = true;
          if (amountsNotEqual(prov.getAmount(), loc.getAmount())) {
            invalidAmountTrans.add(prov);
          }
          if (timestampNotEqual(prov.getTransactionTimestamp(), loc.getTransactionTimestamp())) {
            invalidTimestampTrans.add(prov);
          }
          if (clientIdNotEqual(prov.getClientAccount(), loc.getClientAccount())) {
            invalidClientIdTrans.add(prov);
          }
          break;
        }
      }
      if (!found) {
        missingTrans.add(prov);
      }
    }
    Map<DiscrepancyType, List<Transaction>> results = new HashMap<>();
    results.put(DiscrepancyType.MISSING, missingTrans);
    results.put(DiscrepancyType.INVALID_AMOUNT, invalidAmountTrans);
    results.put(DiscrepancyType.INVALID_CLIENT_ID, invalidClientIdTrans);
    results.put(DiscrepancyType.INVALID_TIMESTAMP, invalidTimestampTrans);
    return results;
  }

  private boolean amountsNotEqual(double amt1, double amt2) {
    if (config.truncateDoubleAmtToInt()) {
      return Integer.compare((int) amt1, (int) amt2) != 0;
    }
    return Double.compare(amt1, amt2) != 0;
  }

  private boolean clientIdNotEqual(String id1, String id2) {
    return !id1.equals(id2);
  }

  private boolean timestampNotEqual(Date d1, Date d2) {
    return d1.compareTo(d2) != 0;
  }
}
