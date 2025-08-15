package com.goglotek.fraud_detector.dataextractionservice.client;

import com.goglotek.fraud_detector.dataextractionservice.configuration.Config;
import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.model.FileType;

public interface Client {
    public byte[] getFile(final String filePath) throws GoglotekException;

    public void saveFile(final byte[] file, final String filename, final FileType type) throws GoglotekException;

    public void deleteFile(final String filename, final FileType type) throws GoglotekException;

    public void setConfig(Config config);

    public Config getConfig();
}
