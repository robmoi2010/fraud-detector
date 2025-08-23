package com.goglotek.frauddetector.datastoreservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.CreateProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.dto.GenericSuccessResponse;
import com.goglotek.frauddetector.datastoreservice.dto.ProviderTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.model.Files;
import com.goglotek.frauddetector.datastoreservice.model.ProviderTransactions;
import com.goglotek.frauddetector.datastoreservice.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goglotek.frauddetector.datastoreservice.dto.FilterModel;
import com.goglotek.frauddetector.datastoreservice.exception.ProviderTransactionsNotFoundException;
import com.goglotek.frauddetector.datastoreservice.service.ProviderTransactionsService;

@RequestMapping("providertransactions")
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class ProviderTransactionsController {
    @Autowired
    ProviderTransactionsService providerTransactionsService;

    @Autowired
    FilesService filesService;

    @Autowired
    Cryptography cryptography;

    @Autowired
    Config config;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ProviderTransactionsDto transactions(@RequestParam(name = "page", required = true) Integer page,
                                                              @RequestParam(name = "limit", required = true) Integer limit,
                                                              @RequestParam(name = "order_by", required = true) String order,
                                                              @RequestParam(name = "direction", required = true) String direction) {
        ProviderTransactionsDto dto = new ProviderTransactionsDto();
        dto.setCount(providerTransactionsService.countAll());
        dto.setTransactions(providerTransactionsService.findAllPaged(page, limit, order, direction));
        return dto;
    }

    @RequestMapping(value = "/{transaction_id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ProviderTransactions transction(
            @PathVariable(value = "transaction_id", required = true) Long transId) {
        return providerTransactionsService.findById(transId).orElseThrow(
                () -> new ProviderTransactionsNotFoundException("Mpesa transaction with id " + transId + " not found"));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ProviderTransactionsDto filteredTransactions(
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "limit", required = true) Integer limit,
            @RequestParam(name = "order_by", required = true) String order,
            @RequestParam(name = "direction", required = true) String direction,
            @RequestBody List<FilterModel> filterModel) {
        ProviderTransactionsDto dto = new ProviderTransactionsDto();
        dto.setCount(providerTransactionsService.countAllFilteredPaged(filterModel));
        dto.setTransactions(
                providerTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
        return dto;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ProviderTransactions> search(@RequestBody String keyword) {
        return providerTransactionsService.search(keyword.trim().toLowerCase());
    }

    @RequestMapping(value = "/create/{file_global_id}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody GenericSuccessResponse createTransactions(@PathVariable(value = "file_global_id", required = true) String fileGlobalId, @RequestBody List<String> encryptedTransactions) throws GoglotekException {
        List<ProviderTransactions> transactions = new ArrayList<>();
        Files file = filesService.getFileByGlobalId(fileGlobalId);
        for (String transaction : encryptedTransactions) {
            byte[] decrypted = cryptography.decrypt(transaction.getBytes(), config.getEncryptionKey());
            try {
                CreateProviderTransactionsDto dto = new ObjectMapper().readValue(decrypted, CreateProviderTransactionsDto.class);
                ProviderTransactions p = new ProviderTransactions();
                p.setClientAccount(dto.getClientAccount());
                p.setAmount(dto.getAmount());
                p.setTransactionId(dto.getId());
                p.setCreatedDate(new Date());
                p.setDetails(dto.getDetails());
                p.setTransactionTime(dto.getTransactionTimestamp());
                p.setFileId(file.getFileId());
                transactions.add(p);
            } catch (IOException e) {
                throw new GoglotekException(e, "json deserialization error:" + e.getMessage());
            }
        }
        providerTransactionsService.createAll(transactions);
        GenericSuccessResponse response = new GenericSuccessResponse();
        response.setMessage("success, " + transactions.size() + " for file " + file.getFileName() + " stored");
        response.setStatusCode(200);
        response.setSuccess(true);
        response.setServerTimestamp(new Date());
        return response;

    }
}
