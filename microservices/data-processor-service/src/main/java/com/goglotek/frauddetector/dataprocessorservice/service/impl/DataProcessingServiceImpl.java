package com.goglotek.frauddetector.dataprocessorservice.service.impl;

import com.goglotek.frauddetector.dataprocessorservice.dto.FileDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.ProcessedTransactionDto;
import com.goglotek.frauddetector.dataprocessorservice.dto.Transaction;
import com.goglotek.frauddetector.dataprocessorservice.service.DataProcessingService;
import com.goglotek.frauddetector.dataprocessorservice.service.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO: for paying clients, batch transaction processing for large transactions. NB: If a companies transactions are to large to process once then they should contract us for a custom proper implementation.
public class DataProcessingServiceImpl implements DataProcessingService {
    @Autowired
    private DataStoreService dataStoreService;

    @Override
    public void processData(List<FileDto> files) {
        List<Transaction> fraudulentTransactions = new ArrayList<>();
        List<Transaction> missingTransactions = new ArrayList<>();
        for (FileDto file : files) {
            //get local and provider transactions from server
            List<Transaction> providerTrans = dataStoreService.getProviderTransactions(file);
            List<Transaction> localTrans = dataStoreService.getLocalTransactions(file);

            //process transactions and get fraudulent and missing transactions
            List<Transaction> fraudulent = getFraudulentTransactions(providerTrans, localTrans);
            List<Transaction> missing = getMissingTransactions(providerTrans, localTrans);

            //send processed transactions to data store
            ProcessedTransactionDto processed = new ProcessedTransactionDto();
            processed.setDateProcessed(new Date());
            processed.setFileGlobalId(file.getFileId());
            processed.setFraudulentTransactions(fraudulent);
            processed.setMissingTransactions(missing);

            //send data back to server
            dataStoreService.storeProcessedTransactionData(processed);
        }
    }

    private List<Transaction> getFraudulentTransactions(List<Transaction> provider, List<Transaction> local) {
        List<Transaction> fraudulentTrans = new ArrayList<>();
        for (Transaction loc : local) {
            boolean found = false;
            for (Transaction prov : provider) {
                if (loc.getId().equals(prov.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                fraudulentTrans.add(loc);
            }
        }
        return fraudulentTrans;
    }

    private List<Transaction> getMissingTransactions(List<Transaction> provider, List<Transaction> local) {
        List<Transaction> missingTrans = new ArrayList<>();
        for (Transaction prov : provider) {
            boolean found = false;
            for (Transaction loc : local) {
                if (prov.getId().equals(loc.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingTrans.add(prov);
            }
        }
        return missingTrans;
    }
}
