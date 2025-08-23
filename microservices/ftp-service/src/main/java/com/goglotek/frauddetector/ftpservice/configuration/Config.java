package com.goglotek.frauddetector.ftpservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
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
    @Value("${goglotek.cryptography.encryption_key}")
    private String encryptionKey;
    @Value("${goglotek.cryptography.init_vector}")
    private String initVector;
    @Value("${goglotek.frauddetector.exchange}")
    private String serviceExchange;
    @Value("${goglotek.ftp.routing_key}")
    private String ftpRoutingKey;
    @Value("${goglotek.ftp.file_type}")
    private String fileType;

    public Config() {

    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRemoteFolder() {
        return remoteFolder;
    }

    public void setRemoteFolder(String remoteFolder) {
        this.remoteFolder = remoteFolder;
    }

    public String getLocalFolder() {
        return localFolder;
    }

    public void setLocalFolder(String localFolder) {
        this.localFolder = localFolder;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getInitVector() {
        return initVector;
    }

    public void setInitVector(String initVector) {
        this.initVector = initVector;
    }

    public String getLocalRootFolder() {
        return localRootFolder;
    }

    public void setLocalRootFolder(String localRootFolder) {
        this.localRootFolder = localRootFolder;
    }

    public String getServiceExchange() {
        return serviceExchange;
    }

    public void setServiceExchange(String serviceExchange) {
        this.serviceExchange = serviceExchange;
    }

    public String getFtpRoutingKey() {
        return ftpRoutingKey;
    }

    public void setFtpRoutingKey(String ftpRoutingKey) {
        this.ftpRoutingKey = ftpRoutingKey;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
