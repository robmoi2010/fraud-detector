package com.goglotek.frauddetector.dataextractionservice.datastoreservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${application.custom.jwt.clientId:user}")
    private String clientId;

    @Value("${application.custom.jwt.client-secret:password}")
    private String clientSecret;

    @Value("${application.custom.jwt.private-key:privateKey}")
    private String privateKey;

    @Value("${application.custom.jwt.public-key:publicKey}")
    private String publicKey;

    @Value("${application.custom.jwt.accessTokenValidititySeconds:43200}") // 12 hours
    private int accessTokenValiditySeconds;

    @Value("${application.custom.jwt.authorizedGrantTypes:password}")
    private String[] authorizedGrantTypes;

    @Value("${application.custom.jwt.refreshTokenValiditySeconds:2592000}") // 30 days
    private int refreshTokenValiditySeconds;
    @Value("${goglotek.cryptography.encryption_key}")
    private String encryptionKey;
    @Value("${goglotek.cryptography.init_vector}")
    private String encryptionInitVector;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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
}
