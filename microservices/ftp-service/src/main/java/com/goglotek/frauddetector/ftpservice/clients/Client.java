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

package com.goglotek.frauddetector.ftpservice.clients;

import com.goglotek.frauddetector.ftpservice.configuration.Config;
import com.goglotek.frauddetector.ftpservice.domain.TransactionsFile;
import com.goglotek.frauddetector.ftpservice.exception.GoglotekException;
import java.util.List;

public interface Client {

  public List<TransactionsFile> downloadFiles() throws GoglotekException;

  public void setConfig(Config config);

  public Config getConfig();
}
