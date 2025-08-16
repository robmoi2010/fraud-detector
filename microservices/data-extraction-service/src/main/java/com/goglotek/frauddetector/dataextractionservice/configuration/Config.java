package com.goglotek.frauddetector.dataextractionservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${goglotek.transactions.staging_folder}")
    private String stagingDir;
    @Value("${goglotek.transactions.error_folder}")
    private String errorDir;
    @Value("${goglotek.transactions.processed_folder}")
    private String processedDir;
    @Value("${goglotek.transactions.unauthorized_folder}")
    private String unauthorizedDir;
    @Value("${goglotek.cryptography.encryption_key}")
    private String encryptionKey;
    @Value("${goglotek.cryptography.init_vector}")
    private String encryptionInitVector;
    @Value("${goglotek.frauddetector.exchange}")
    private String filesExchange;
    @Value("${goglotek.ftp.routing_key}")
    private String filesRoutingKey;
    @Value("${goglotek.pushfiles.endpoint}")
    private String pushFilesEndpoint;
    @Value("${goglotek.pushtransactions.endpoint}")
    private String pushTransactionsEndpoint;
    @Value("${goglotek.base_url}")
    private String baseUrl;
    @Value("${goglotek.OauthId}")
    private String OauthId;
    @Value("${goglotek.OauthSecret}")
    private String OauthSecret;
    @Value("${goglotek.webservice.username}")
    private String wsUsername;
    @Value("${goglotek.webservice.password}")
    private String wsPassword;

    public String getStagingDir() {
        return stagingDir;
    }

    public void setStagingDir(String stagingDir) {
        this.stagingDir = stagingDir;
    }

    public String getErrorDir() {
        return errorDir;
    }

    public void setErrorDir(String errorDir) {
        this.errorDir = errorDir;
    }

    public String getProcessedDir() {
        return processedDir;
    }

    public void setProcessedDir(String processedDir) {
        this.processedDir = processedDir;
    }

    public String getUnauthorizedDir() {
        return unauthorizedDir;
    }

    public void setUnauthorizedDir(String unauthorizedDir) {
        this.unauthorizedDir = unauthorizedDir;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getEncryptionInitVector() {
        return encryptionInitVector;
    }

    public void setEncryptionInitVector(String encryptionInitVector) {
        this.encryptionInitVector = encryptionInitVector;
    }

    public String getFilesExchange() {
        return filesExchange;
    }

    public void setFilesExchange(String filesExchange) {
        this.filesExchange = filesExchange;
    }

    public String getFilesRoutingKey() {
        return filesRoutingKey;
    }

    public void setFilesRoutingKey(String filesRoutingKey) {
        this.filesRoutingKey = filesRoutingKey;
    }

    public String getPushFilesEndpoint() {
        return pushFilesEndpoint;
    }

    public void setPushFilesEndpoint(String pushFilesEndpoint) {
        this.pushFilesEndpoint = pushFilesEndpoint;
    }

    public String getPushTransactionsEndpoint() {
        return pushTransactionsEndpoint;
    }

    public void setPushTransactionsEndpoint(String pushTransactionsEndpoint) {
        this.pushTransactionsEndpoint = pushTransactionsEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOauthId() {
        return OauthId;
    }

    public void setOauthId(String oauthId) {
        OauthId = oauthId;
    }

    public String getOauthSecret() {
        return OauthSecret;
    }

    public void setOauthSecret(String oauthSecret) {
        OauthSecret = oauthSecret;
    }

    public String getWsUsername() {
        return wsUsername;
    }

    public void setWsUsername(String wsUsername) {
        this.wsUsername = wsUsername;
    }

    public String getWsPassword() {
        return wsPassword;
    }

    public void setWsPassword(String wsPassword) {
        this.wsPassword = wsPassword;
    }
}
