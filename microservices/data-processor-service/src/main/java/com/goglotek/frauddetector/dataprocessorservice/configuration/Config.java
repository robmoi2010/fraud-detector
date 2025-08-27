package com.goglotek.frauddetector.dataprocessorservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${goglotek.getfiles.endpoint}")
    private String getFilesEndpoint;

    @Value("${goglotek.getprovidertransactions.endpoint}")
    private String getProviderTransactionsEndpoint;

    @Value("${goglotek.getlocaltransactions.endpoint}")
    private String getLocalTransactionsEndpoint;

    @Value("${goglotek.base_url}")
    private String baseUrl;

    @Value("${truncate_double_to_int}")
    private boolean truncateDoubleAmtToInt;

    @Value("${goglotek.webservice.token_endpoint}")
    private String tokenEndpoint;

    @Value("${goglotek.webservice.client_id}")
    private String clientId;

    @Value("${goglotek.webservice.client_secret}")
    private String clientSecret;

    @Value("${goglotek.webservice.scope}")
    private String scope;

    @Value("${goglotek.frauddetector.exchange}")
    private String processingExchange;

    @Value("${goglotek.frauddetector.queue}")
    private String queue;

    @Value("${goglotek.processing.routing_key}")
    private String routingKey;

    @Value("${goglotek.core.system_timestamp_format}")
    private String internalTimestampFormat;

    public String getInternalTimestampFormat() {
        return internalTimestampFormat;
    }

    public void setInternalTimestampFormat(String internalTimestampFormat) {
        this.internalTimestampFormat = internalTimestampFormat;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getProcessingExchange() {
        return processingExchange;
    }

    public void setProcessingExchange(String processingExchange) {
        this.processingExchange = processingExchange;
    }

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

    public String getGetProviderTransactionsEndpoint() {
        return getProviderTransactionsEndpoint;
    }

    public void setGetProviderTransactionsEndpoint(String getProviderTransactionsEndpoint) {
        this.getProviderTransactionsEndpoint = getProviderTransactionsEndpoint;
    }

    public String getGetLocalTransactionsEndpoint() {
        return getLocalTransactionsEndpoint;
    }

    public void setGetLocalTransactionsEndpoint(String getLocalTransactionsEndpoint) {
        this.getLocalTransactionsEndpoint = getLocalTransactionsEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isTruncateDoubleAmtToInt() {
        return truncateDoubleAmtToInt;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
