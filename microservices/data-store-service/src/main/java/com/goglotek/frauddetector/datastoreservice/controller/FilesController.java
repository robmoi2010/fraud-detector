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

package com.goglotek.frauddetector.datastoreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.dto.FilesDto;
import com.goglotek.frauddetector.datastoreservice.exception.FileNotFoundException;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.exception.InvalidEncryptionKeyException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'MACHINE_USER')")
public class FilesController {

  @Autowired
  FilesService filesService;

  @Autowired
  Cryptography cryptography;

  @Autowired
  Config config;

  @Autowired
  private ObjectMapper objectMapper;

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody FilesDto files(@RequestParam(name = "page", required = true) Integer page,
      @RequestParam(name = "limit", required = true) Integer limit,
      @RequestParam(name = "order_by", required = true) String order,
      @RequestParam(name = "direction", required = true) String direction) {
    FilesDto dto = new FilesDto();
    dto.setCount(filesService.countAll());
    dto.setFiles(filesService.findAllPaged(page, limit, order, direction));
    return dto;
  }

  @RequestMapping(value = "/{file_id}", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody Files file(@PathVariable(value = "file_id", required = false) Long fileId) {
    return filesService.findById(fileId)
        .orElseThrow(() -> new FileNotFoundException("File with id " + fileId + " not found"));
  }

  @RequestMapping(value = "/globalid/{file_Global_id}", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody Files fileByGlobalId(
      @PathVariable(value = "file_Global_id", required = true) String fileId)
      throws GoglotekException {
    return filesService.getFileByGlobalId(fileId);
  }

  @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody FilesDto filteredFiles(
      @RequestParam(name = "page", required = true) Integer page,
      @RequestParam(name = "limit", required = true) Integer limit,
      @RequestParam(name = "order_by", required = true) String order,
      @RequestParam(name = "direction", required = true) String direction,
      @RequestParam(name = "filter_column", required = true) String filterColumn,
      @RequestParam(name = "operator_value", required = true) String operatorValue,
      @RequestParam(name = "value", required = true) String value) {
    FilesDto dto = new FilesDto();
    dto.setCount(filesService.countAllFilteredPaged(filterColumn, operatorValue, value));
    dto.setFiles(filesService.findAllFilteredPaged(page, limit, order, direction, filterColumn,
        operatorValue, value));
    return dto;
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<Files> search(@RequestBody String text) {
    return filesService.find(text.trim().toLowerCase());
  }


  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody Files createFile(@RequestBody String encryptedFileData)
      throws GoglotekException {
    Authentication a = SecurityContextHolder.getContext().getAuthentication();
    byte[] decrypted;
    try {
      decrypted = cryptography.decrypt(Base64.getDecoder().decode(encryptedFileData.getBytes()),
          config.getEncryptionKey(), config.getEncryptionInitVector());
    } catch (GoglotekException e) {
      throw new InvalidEncryptionKeyException(e,
          "Invalid encryption key. Send file encrypted with a key recognized by the server.");
    }
    CreateFileDto file;
    try {
      file = objectMapper.readValue(decrypted, CreateFileDto.class);
    } catch (IOException e) {
      throw new GoglotekException(e, "json deserialization error:" + e.getMessage());
    }
    return filesService.createFile(file);
  }
}
