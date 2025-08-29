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

package com.goglotek.frauddetector.dataextractionservice.event;

import com.goglotek.frauddetector.dataextractionservice.dto.FileDto;
import com.goglotek.frauddetector.dataextractionservice.dto.TransactionsDto;
import com.goglotek.frauddetector.dataextractionservice.extractors.CsvExtractor;
import com.goglotek.frauddetector.dataextractionservice.service.DataProcessingService;
import com.goglotek.frauddetector.dataextractionservice.service.DataStoreService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

  static final Logger logger = LogManager.getLogger(CsvExtractor.class);
  @Autowired
  private DataProcessingService dataProcessingService;

  @Autowired
  private DataStoreService dataStoreService;

  @Autowired
  private EventDispatcher eventDispatcher;

  @RabbitListener(queues = "${goglotek.ftp.queue}")
  void handleFtpFileDownload(final List<FileDto> files) {
    try {
      //TODO: for paying clients, send both file and its transactions data combined
      logger.info("Received " + files.size() + " files for data extraction");
      List<FileDto> newList = new ArrayList<>();
      for (FileDto file : files) {
        try {
          TransactionsDto transactionsDto = dataProcessingService.extractFilesData(file);

          file.setTotalTransactions(transactionsDto.getTransactions().size());
          file.setGroupAccount(transactionsDto.getTransactions().get(0).getGroupAccount());
          file.setFromDate(transactionsDto.getFromDate());
          file.setToDate(transactionsDto.getToDate());

          //send file data
          String resp = dataStoreService.storeFile(file);

          //send transaction to datastore service
          String transResp = dataStoreService.storeTransactions(transactionsDto.getTransactions(),
              file.getFileId());
          if (resp.contains(file.getFileName()) && transResp.toLowerCase().contains("success")) {
            newList.add(file);
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }

      //send files to be processed by the data processor
      if (!newList.isEmpty()) {
        eventDispatcher.send(newList);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }
}
