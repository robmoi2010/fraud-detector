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

package com.goglotek.frauddetector.ftpservice.service.impl;

import com.goglotek.frauddetector.ftpservice.clients.Client;
import com.goglotek.frauddetector.ftpservice.domain.TransactionsFile;
import com.goglotek.frauddetector.ftpservice.event.EventDispatcher;
import com.goglotek.frauddetector.ftpservice.exception.GoglotekException;
import com.goglotek.frauddetector.ftpservice.service.FtpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FtpServiceImpl implements FtpService {

  @Autowired
  private EventDispatcher eventDispatcher;

  @Autowired
  private Client client;

  @Override
  public void downloadFiles()
      throws GoglotekException {
    List<TransactionsFile> files = client.downloadFiles();
    if (!files.isEmpty()) {
      eventDispatcher.send(files);
    }
  }

}
