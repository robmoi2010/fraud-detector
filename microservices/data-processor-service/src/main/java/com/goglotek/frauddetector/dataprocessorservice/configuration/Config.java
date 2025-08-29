/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

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

  @Value("${goglotek.processedtransactions.endpoint}")
  private String processedTransEndpoint;

  @Value("${goglotek.cryptography.encryption_key}")
  private String encryptionKey;

  @Value("${goglotek.cryptography.init_vector}")
  private String initVector;

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

  public String getProcessedTransEndpoint() {
    return processedTransEndpoint;
  }

  public void setProcessedTransEndpoint(String processedTransEndpoint) {
    this.processedTransEndpoint = processedTransEndpoint;
  }

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
