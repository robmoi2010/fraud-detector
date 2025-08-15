package com.goglotek.fraud_detector.ftpservice.service.impl;

import java.util.List;

import com.goglotek.fraud_detector.ftpservice.clients.Client;
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

    @Value("${goglotek.ftp.remote_host}")
    private String remoteHost;
    @Value("${goglotek.ftp.username}")
    private String username;
    @Value("${goglotek.ftp.password}")
    private String password;
    @Value("${goglotek.ftp.port}")
    private int port;
    @Value("${goglotek.ftp.remote_folder}")
    private String remoteFolder;
    @Value("${goglotek.ftp.local_folder}")
    private String localFolder;
    @Value("${goglotek.ftp.local_root_folder}")
    private String localRootFolder;
    @Value("${goglotek.cypher.encryption_key}")
    private String encryptionKey;
    @Value("${goglotek.cypher.init_vector}")
    private String initVector;

    public FtpServiceImpl() {

    }


    @Override
    public void downloadFiles()
            throws GoglotekException {
        List<TransactionsFile> files = client.downloadFiles();
        if (!files.isEmpty()) {
            eventDispatcher.send(files);
        }
    }

}
