package com.goglotek.fraud_detector.ftpservice.clients;

import com.goglotek.fraud_detector.ftpservice.configuration.Config;
import com.goglotek.fraud_detector.ftpservice.domain.TransactionsFile;
import com.goglotek.fraud_detector.ftpservice.exception.GoglotekException;

import java.util.List;

public interface Client {
    public List<TransactionsFile> downloadFiles() throws GoglotekException;
    public void setConfig(Config config);
    public Config getConfig();
}
