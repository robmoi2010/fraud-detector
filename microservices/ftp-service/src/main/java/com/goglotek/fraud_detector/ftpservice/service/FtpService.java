package com.goglotek.fraud_detector.ftpservice.service;

import com.goglotek.fraud_detector.ftpservice.exception.GoglotekException;

public interface FtpService {
    public void downloadFiles()
            throws GoglotekException;
}
