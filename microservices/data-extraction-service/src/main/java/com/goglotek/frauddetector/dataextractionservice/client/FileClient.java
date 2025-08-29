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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileClient implements Client {

  @Autowired
  private Config config;

  private final String dirSeparator = FileSystems.getDefault().getSeparator();

  @Override
  public byte[] getFile(final String filePath) throws GoglotekException {
    Path path = Paths.get(filePath);
    byte[] encypted = null;
    try {
      encypted = Files.readAllBytes(path);
    } catch (IOException e) {
      throw new GoglotekException(e, "File read error: " + e.getMessage());
    }
    return encypted;
  }

  @Override
  public void saveFile(final byte[] file, final String absolutePath, final FileType type)
      throws GoglotekException {
    String path = getPath(absolutePath, type);
    Path p = Paths.get(path);
    try {
      if (Files.notExists(p)) {
        Files.createDirectory(p);
      }
      try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file)) {
        Files.copy(inputStream, Paths.get(
                path + dirSeparator + absolutePath.substring(absolutePath.lastIndexOf(dirSeparator))),
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (Exception e) {
      throw new GoglotekException(e, "File save error: " + e.getMessage());
    }

  }

  private String getPath(String absolutePath, FileType type) {
    String[] p = absolutePath.split(Pattern.quote(dirSeparator));
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < p.length - 2; i++) {
      sb.append(p[i]).append(dirSeparator);
    }
    String path = sb.toString();
    if (type == FileType.ERROR) {
      path += this.config.getErrorDir();
    } else if (type == FileType.PROCESSED) {
      path += this.config.getProcessedDir();
    } else if (type == FileType.UNAUTHORIZED) {
      path += this.config.getUnauthorizedDir();
    } else if (type == FileType.UNPROCESSED) {
      path += this.config.getStagingDir();
    }
    return path;
  }

  @Override
  public void deleteFile(final String absolutePath) throws GoglotekException {
    Path p = Paths.get(absolutePath);
    try {
      Files.deleteIfExists(p);
    } catch (IOException e) {
      throw new GoglotekException(e);
    }
  }

  @Override
  public void setConfig(Config config) {
    this.config = config;
  }

  @Override
  public Config getConfig() {
    return this.config;
  }

}
