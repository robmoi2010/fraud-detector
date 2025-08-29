package com.goglotek.frauddetector.datastoreservice.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.goglotek.frauddetector.datastoreservice.cryptography.Cryptography;
import com.goglotek.frauddetector.datastoreservice.dto.GenericSuccessResponse;
import com.goglotek.frauddetector.datastoreservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import com.goglotek.frauddetector.datastoreservice.exception.InvalidEncryptionKeyException;
import com.goglotek.frauddetector.datastoreservice.model.ProviderLocalTransactions;
import org.apache.hc.core5.http.HttpStatus;
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
import com.goglotek.frauddetector.datastoreservice.dto.ProviderLocalTransactionsDto;
import com.goglotek.frauddetector.datastoreservice.exception.ProviderLocalTransactionsNotFoundException;
import com.goglotek.frauddetector.datastoreservice.service.ProviderLocalTransactionsService;

@RequestMapping("providerlocaltransactions")
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'MACHINE_USER')")
public class ProviderLocalTransactionsController {
    @Autowired
    private ProviderLocalTransactionsService providerLocalTransactionsService;

    @Autowired
    private Cryptography cryptography;

    @Autowired
    private Config config;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ProviderLocalTransactions> providerLocalTransactions(
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "limit", required = true) Integer limit,
            @RequestParam(name = "order_by", required = true) String order,
            @RequestParam(name = "direction", required = true) String direction) {
        return providerLocalTransactionsService.findAllPaged(page, limit, order,
                direction);
    }

    @RequestMapping(value = "/{provider_local_transaction_id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ProviderLocalTransactions providerLocalTransactions(
            @PathVariable(value = "provider_local_transaction_id", required = true) Long transactionId) {
        return providerLocalTransactionsService.findById(transactionId)
                .orElseThrow(() -> new ProviderLocalTransactionsNotFoundException(
                        "transaction with id " + transactionId + " is not found"));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ProviderLocalTransactionsDto filteredTransactions(
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "limit", required = true) Integer limit,
            @RequestParam(name = "order_by", required = true) String order,
            @RequestParam(name = "direction", required = true) String direction,
            @RequestBody List<FilterModel> filterModel) {
        ProviderLocalTransactionsDto dto = new ProviderLocalTransactionsDto();
        dto.setCount(providerLocalTransactionsService.countAllFilteredPaged(filterModel));
        dto.setProviderLocalTransactions(
                providerLocalTransactionsService.findAllFilteredPaged(page, limit, order, direction, filterModel));
        return dto;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ProviderLocalTransactions> search(@RequestBody String keyword) {
        return providerLocalTransactionsService.search(keyword.trim().toLowerCase());
    }

    @RequestMapping(value = "/processed", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody GenericSuccessResponse updateProcessedTransactions(@RequestBody String encryptedProcessedTransactions) throws GoglotekException {
        byte[] decrypted;
        try {
            decrypted = cryptography.decrypt(Base64.getDecoder().decode(encryptedProcessedTransactions.getBytes()), config.getEncryptionKey(), config.getEncryptionInitVector());
        } catch (GoglotekException e) {
            throw new InvalidEncryptionKeyException("Invalid encryption key. Send file encrypted with a key recognized by the server.");
        }

        ProcessedTransactionDto processedTrans;
        try {
            processedTrans = mapper.readValue(decrypted, new TypeReference<ProcessedTransactionDto>() {
            });
        } catch (IOException e) {
            throw new GoglotekException(e, "json deserialization error:" + e.getMessage());
        }

        providerLocalTransactionsService.storeProcessedTransactions(processedTrans);

        GenericSuccessResponse response = new GenericSuccessResponse();
        response.setServerTimestamp(new Date());
        response.setMessage("Successfully stored the processed transactions");
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.SC_SUCCESS);

        return response;

    }
}
