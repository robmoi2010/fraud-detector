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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.Transaction;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import com.goglotek.frauddetector.dataextractionservice.rest.RestClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DataStoreServiceImplTests extends AbstractTests {

  @Autowired
  private DataStoreServiceImpl dataStoreService;

  @MockitoBean
  private RestClient restClient;

  private String postSuccessMessage = "Successfully sent";
  private FileDto fileDto;
  private List<Transaction> transactionList;

  @BeforeEach
  public void setUp() throws Exception {
    //mock post request
    when(restClient.post(anyString(), anyString())).thenReturn(postSuccessMessage);

    //create file dto
    fileDto = new FileDto();
    fileDto.setFileId(UUID.randomUUID().toString());
    fileDto.setFileName("transactions.csv");

    //create transactions
    transactionList = new ArrayList<>();
    Transaction t = new Transaction();
    t.setCreatedOn(new Date());
    t.setAmount(100d);
    t.setClientAccount("1235");
    transactionList.add(t);

    Transaction t1 = new Transaction();
    t1.setCreatedOn(new Date());
    t1.setAmount(1000d);
    t1.setClientAccount("1237565");
    transactionList.add(t1);
  }

  @Test
  public void shouldStoreFile() throws GoglotekException {
    String s = dataStoreService.storeFile(fileDto);
    assertTrue(s.equals(postSuccessMessage));
  }

  @Test
  public void shouldStoreTransactions() throws GoglotekException {
    String s = dataStoreService.storeTransactions(transactionList, fileDto.getFileId());
    assertTrue(s.equals(postSuccessMessage));
  }


}
