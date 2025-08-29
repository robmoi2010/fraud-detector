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

package com.goglotek.frauddetector.dataprocessorservice;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class AbstractTests {

  @MockitoBean
  protected DataStoreService dataStoreService;
  protected List<Transaction> localTransactions;
  protected List<Transaction> providerTransactions;
  protected int localTransCount = 15;
  protected int providerTransCount = 10;
  private Date timestamp;

  public void startUp() throws Exception {
    timestamp = new Date();
    //create local transactions
    localTransactions = createLocalTransactions();

    //create provider transactions
    providerTransactions = createProviderTransactions();

    //mock data store service
    when(dataStoreService.getLocalTransactions(any(FileDto.class))).thenReturn(localTransactions);
    when(dataStoreService.getProviderTransactions(any(FileDto.class))).thenReturn(
        providerTransactions);
    doNothing().when(dataStoreService)
        .storeProcessedTransactionData(any(ProcessedTransactionDto.class));
  }

  private List<Transaction> createLocalTransactions() {
    List<Transaction> t = new ArrayList<>();
    for (int i = 0; i < localTransCount; i++) {
      Transaction tr = new Transaction();
      tr.setCreatedOn(new Date());
      tr.setAmount((i + 1) * 100d);
      tr.setId("id" + (i + 1));
      tr.setClientAccount("12345" + i);
      Calendar c = Calendar.getInstance();
      c.setTime(timestamp);
      c.add(Calendar.HOUR, i);
      tr.setTransactionTimestamp(c.getTime());
      t.add(tr);
    }
    return t;
  }

  private List<Transaction> createProviderTransactions() {
    List<Transaction> t = new ArrayList<>();
    for (int i = 0; i < providerTransCount; i++) {
      Transaction tr = new Transaction();
      tr.setCreatedOn(new Date());
      tr.setAmount((i + 1) * 100d);
      tr.setId("id" + (i + 1));
      tr.setClientAccount("12345" + i);
      Calendar c = Calendar.getInstance();
      c.setTime(timestamp);
      c.add(Calendar.HOUR, i);
      tr.setTransactionTimestamp(c.getTime());
      t.add(tr);
    }
    return t;
  }
}
