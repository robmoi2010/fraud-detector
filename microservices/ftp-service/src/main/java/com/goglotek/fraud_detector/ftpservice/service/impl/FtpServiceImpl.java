package com.goglotek.fraud_detector.ftpservice.service.impl;

import java.util.List;

import com.goglotek.fraud_detector.ftpservice.clients.Client;
import com.goglotek.fraud_detector.ftpservice.configuration.Config;
import com.goglotek.fraud_detector.ftpservice.exception.GoglotekException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.goglotek.fraud_detector.ftpservice.domain.TransactionsFile;
import com.goglotek.fraud_detector.ftpservice.event.EventDispatcher;
import com.goglotek.fraud_detector.ftpservice.service.FtpService;

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
