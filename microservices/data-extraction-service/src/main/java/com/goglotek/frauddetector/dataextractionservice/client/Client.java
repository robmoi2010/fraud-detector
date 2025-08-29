
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

package com.goglotek.frauddetector.dataextractionservice.client;

import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import com.goglotek.frauddetector.dataextractionservice.dto.FileType;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;

public interface Client {

  public byte[] getFile(final String filePath) throws GoglotekException;

  public void saveFile(final byte[] file, final String filename, final FileType type)
      throws GoglotekException;


  void deleteFile(String absolutePath) throws GoglotekException;

  public void setConfig(Config config);

  public Config getConfig();
}
