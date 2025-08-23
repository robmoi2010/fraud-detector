package com.goglotek.frauddetector.ftpservice.service.impl;

import java.util.List;

import com.goglotek.frauddetector.ftpservice.clients.Client;
import com.goglotek.frauddetector.ftpservice.exception.GoglotekException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.frauddetector.ftpservice.domain.TransactionsFile;
import com.goglotek.frauddetector.ftpservice.event.EventDispatcher;
import com.goglotek.frauddetector.ftpservice.service.FtpService;

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
