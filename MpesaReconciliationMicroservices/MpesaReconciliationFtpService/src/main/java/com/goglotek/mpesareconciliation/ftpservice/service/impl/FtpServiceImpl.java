package com.goglotek.mpesareconciliation.ftpservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goglotek.mpesareconciliation.ftpservice.clients.FtpClient;
import com.goglotek.mpesareconciliation.ftpservice.domain.MpesaFileDto;
import com.goglotek.mpesareconciliation.ftpservice.event.EventDispatcher;
import com.goglotek.mpesareconciliation.ftpservice.service.FtpService;

@Service
public class FtpServiceImpl implements FtpService {
    @Autowired
    private EventDispatcher eventDispatcher;

    @Override
    public void downloadFiles(final String remoteHost, final String username, final String password, final int port,
                              final String remoteFolder, final String localRootFolder, final String localFolder, final String encryptionKey, final String initVector)
            throws Exception {
        List<MpesaFileDto> files = new FtpClient(remoteHost, username, password, port, remoteFolder, localRootFolder, localFolder,
                encryptionKey, initVector).downloadFiles();
        if (!files.isEmpty()) {
            eventDispatcher.send(files);
        }
    }

}
