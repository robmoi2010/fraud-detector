package com.goglotek.frauddetector.ftpservice.service;

import com.goglotek.frauddetector.ftpservice.exception.GoglotekException;

public interface FtpService {
    public void downloadFiles()
            throws GoglotekException;
}
