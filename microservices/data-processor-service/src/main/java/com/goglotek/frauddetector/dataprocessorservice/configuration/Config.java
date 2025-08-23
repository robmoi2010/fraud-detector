package com.goglotek.frauddetector.dataprocessorservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${goglotek.getfiles.endpoint}")
    private String getFilesEndpoint;
    @Value("${goglotek.gettransactions.endpoint}")
    private String getTransactionsEndpoint;
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
    @Value("${truncate_double_to_int}")
    private boolean truncateDoubleAmtToInt;

    public boolean truncateDoubleAmtToInt() {
        return truncateDoubleAmtToInt;
    }

    public void setTruncateDoubleAmtToInt(boolean truncateDoubleAmtToInt) {
        this.truncateDoubleAmtToInt = truncateDoubleAmtToInt;
    }

    public String getGetFilesEndpoint() {
        return getFilesEndpoint;
    }

    public void setGetFilesEndpoint(String getFilesEndpoint) {
        this.getFilesEndpoint = getFilesEndpoint;
    }

    public String getGetTransactionsEndpoint() {
        return getTransactionsEndpoint;
    }

    public void setGetTransactionsEndpoint(String getTransactionsEndpoint) {
        this.getTransactionsEndpoint = getTransactionsEndpoint;
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
