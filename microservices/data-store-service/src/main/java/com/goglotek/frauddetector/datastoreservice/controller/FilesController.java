package com.goglotek.frauddetector.datastoreservice.controller;

import java.io.IOException;
import java.util.List;

import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateFileDto;
import com.goglotek.frauddetector.datastoreservice.dto.CreateUserDto;
import com.goglotek.frauddetector.datastoreservice.dto.FilesDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.exception.InvalidEncryptionKeyException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.model.Users;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.datastoreservice.exception.FileNotFoundException;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;

@RestController
@RequestMapping("/files")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class FilesController {
    @Autowired
    FilesService filesService;
    @Autowired
    Cryptography cryptography;
    @Autowired
    Config config;

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
                .orElseThrow(() -> new FileNotFoundException("Mpesa file with id " + fileId + " not found"));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody FilesDto filteredFiles(@RequestParam(name = "page", required = true) Integer page,
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
    public @ResponseBody Files createFile(@RequestBody String encryptedFileData) throws GoglotekException {
        byte[] decrypted;
        try {
            decrypted = cryptography.decrypt(encryptedFileData.getBytes(), config.getEncryptionKey(), config.getEncryptionInitVector());
        } catch (GoglotekException e) {
            throw new InvalidEncryptionKeyException(e, "Invalid encryption key:" + e.getMessage());
        }
        if (decrypted != null) {
            CreateFileDto file;
            try {
                file = new ObjectMapper().readValue(decrypted, CreateFileDto.class);
            } catch (IOException e) {
                throw new GoglotekException(e, "json deserialization error:" + e.getMessage());
            }
            if (file != null) {

            }
        }


    }

}
