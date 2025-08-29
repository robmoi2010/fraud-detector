package com.goglotek.frauddetector.datastoreservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${application.custom.jwt.dataextractor.clientId}")
    private String dataextractorClientId;

    @Value("${application.custom.jwt.dataextractor.client-secret}")
    private String dataextractorClientSecret;

    @Value("${application.custom.jwt.dataprocessor.clientId}")
    private String dataprocessorClientId;

    @Value("${application.custom.jwt.dataprocessor.client-secret}")
    private String dataprocessorClientSecret;

    @Value("${application.custom.jwt.ui.clientId}")
    private String uiClientId;

    @Value("${application.custom.jwt.ui.client-secret}")
    private String uiClientSecret;

    @Value("${application.custom.jwt.private-key}")
    private String privateKey;

    @Value("${application.custom.jwt.public-key}")
    private String publicKey;

    @Value("${application.custom.jwt.accessTokenValidititySeconds}") // 12 hours
    private int accessTokenValiditySeconds;

    @Value("${application.custom.jwt.authorizedGrantTypes}")
    private String[] authorizedGrantTypes;

    @Value("${application.custom.jwt.refreshTokenValiditySeconds}") // 30 days
    private int refreshTokenValiditySeconds;

    @Value("${goglotek.cryptography.encryption_key}")
    private String encryptionKey;

    @Value("${goglotek.cryptography.init_vector}")
    private String encryptionInitVector;

    @Value("${application.custom.jwt.redirectUrl}")
    private String redirectUrl;

    @Value("${server.port}")
    private String serverPort;

    @Value("${application.custom.jwt.test.clientId}")
    private String testClientId;

    @Value("${application.custom.jwt.test.client-secret}")
    private String testClientSecret;

    @Value("${application.custom.jwt.client-credentials-role-name}")
    private String machineRoleName;

    @Value("${goglotek.core.system_timestamp_format}")
    private String internalTimestampFormat;

    public String getInternalTimestampFormat() {
        return internalTimestampFormat;
    }

    public void setInternalTimestampFormat(String internalTimestampFormat) {
        this.internalTimestampFormat = internalTimestampFormat;
    }

    public String getMachineRoleName() {
        return machineRoleName;
    }

    public void setMachineRoleName(String machineRoleName) {
        this.machineRoleName = machineRoleName;
    }

    public String getTestClientId() {
        return testClientId;
    }

    public void setTestClientId(String testClientId) {
        this.testClientId = testClientId;
    }

    public String getTestClientSecret() {
        return testClientSecret;
    }

    public void setTestClientSecret(String testClientSecret) {
        this.testClientSecret = testClientSecret;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getDataextractorClientId() {
        return dataextractorClientId;
    }

    public void setDataextractorClientId(String dataextractorClientId) {
        this.dataextractorClientId = dataextractorClientId;
    }

    public String getDataextractorClientSecret() {
        return dataextractorClientSecret;
    }

    public void setDataextractorClientSecret(String dataextractorClientSecret) {
        this.dataextractorClientSecret = dataextractorClientSecret;
    }

    public String getDataprocessorClientId() {
        return dataprocessorClientId;
    }

    public void setDataprocessorClientId(String dataprocessorClientId) {
        this.dataprocessorClientId = dataprocessorClientId;
    }

    public String getDataprocessorClientSecret() {
        return dataprocessorClientSecret;
    }

    public void setDataprocessorClientSecret(String dataprocessorClientSecret) {
        this.dataprocessorClientSecret = dataprocessorClientSecret;
    }

    public String getUiClientId() {
        return uiClientId;
    }

    public void setUiClientId(String uiClientId) {
        this.uiClientId = uiClientId;
    }

    public String getUiClientSecret() {
        return uiClientSecret;
    }

    public void setUiClientSecret(String uiClientSecret) {
        this.uiClientSecret = uiClientSecret;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public String[] getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String[] authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public int getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
