package com.goglotek.fraud_detector.dataextractionservice.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.goglotek.fraud_detector.dataextractionservice.configuration.Config;
import com.goglotek.fraud_detector.dataextractionservice.exception.GoglotekException;
import com.goglotek.fraud_detector.dataextractionservice.model.FileType;
import org.springframework.beans.factory.annotation.Autowired;

public class FileClient implements Client {
    @Autowired
    private Config config;
    private final String dirSeparator = FileSystems.getDefault().getSeparator();

    @Override
    public byte[] getFile(final String filePath) throws GoglotekException {
        Path path = Paths.get(filePath);
        byte[] encypted = null;
        try {
            encypted = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new GoglotekException(e, "File read error: " + e.getMessage());
        }
        return encypted;
    }

    @Override
    public void saveFile(final byte[] file, final String filename, final FileType type) throws GoglotekException {
        String path = "";
        if (type == FileType.ERROR) {
            path = this.config.getErrorDir() + dirSeparator + filename;
        } else if (type == FileType.PROCESSED) {
            path = this.config.getProcessedDir() + dirSeparator + filename;
        } else if (type == FileType.UNAUTHORIZED) {
            path = this.config.getUnauthorizedDir() + dirSeparator + filename;
        } else if (type == FileType.UNPROCESSED) {
            path = this.config.getStagingDir() + dirSeparator + filename;
        }
        Path p = Paths.get(path);
        try (InputStream input = new ByteArrayInputStream(file)) {
            try {
                Files.copy(input, p, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new GoglotekException(e);
            }
        } catch (IOException e) {
            throw new GoglotekException(e);
        }
    }

    @Override
    public void deleteFile(final String filename, final FileType type) throws GoglotekException {
        Path p = null;
        if (type == FileType.ERROR) {
            p = Paths.get(this.config.getErrorDir() + dirSeparator + filename);
        } else if (type == FileType.PROCESSED) {
            p = Paths.get(this.config.getProcessedDir() + dirSeparator + filename);
        } else if (type == FileType.UNAUTHORIZED) {
            p = Paths.get(this.config.getUnauthorizedDir() + dirSeparator + filename);
        } else if (type == FileType.UNPROCESSED) {
            p = Paths.get(this.config.getStagingDir() + dirSeparator + filename);
        }
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {
            throw new GoglotekException(e);
        }
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

}
