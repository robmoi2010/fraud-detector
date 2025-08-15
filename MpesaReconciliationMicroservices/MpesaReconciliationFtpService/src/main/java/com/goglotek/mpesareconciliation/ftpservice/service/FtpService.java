package com.goglotek.mpesareconciliation.ftpservice.service;

public interface FtpService {
    public void downloadFiles(final String remoteHost, final String username, final String password, final int port,
                              final String remoteFolder, final String localRootFolder, final String localFolder, final String encryptionKey, final String initVector)
            throws Exception;
}
