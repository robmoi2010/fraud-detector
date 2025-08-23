package com.goglotek.frauddetector.ftpservice.clients;

import com.goglotek.frauddetector.ftpservice.configuration.Config;
import com.goglotek.frauddetector.ftpservice.domain.TransactionsFile;
import com.goglotek.frauddetector.ftpservice.exception.GoglotekException;

import java.util.List;

public interface Client {
    public List<TransactionsFile> downloadFiles() throws GoglotekException;
    public void setConfig(Config config);
    public Config getConfig();
}
