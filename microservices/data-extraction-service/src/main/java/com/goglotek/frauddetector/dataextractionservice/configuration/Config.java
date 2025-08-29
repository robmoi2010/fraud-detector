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

  @Value("${goglotek.webservice.token_endpoint}")
  private String tokenEndpoint;

  @Value("${goglotek.webservice.client_id}")
  private String clientId;

  @Value("${goglotek.webservice.client_secret}")
  private String clientSecret;

  @Value("${goglotek.webservice.scope}")
  private String scope;

  @Value("${goglotek.ftp.queue}")
  private String filesQueue;

  @Value("${goglotek.processing.exchange}")
  private String processingExchange;

  @Value("${goglotek.processing.routing_key}")
  private String processingRoutingKey;

  @Value("${goglotek.core.system_timestamp_format}")
  private String internalTimestampFormat;

  public String getInternalTimestampFormat() {
    return internalTimestampFormat;
  }

  public void setInternalTimestampFormat(String internalTimestampFormat) {
    this.internalTimestampFormat = internalTimestampFormat;
  }

  public String getProcessingExchange() {
    return processingExchange;
  }

  public void setProcessingExchange(String processingExchange) {
    this.processingExchange = processingExchange;
  }

  public String getProcessingRoutingKey() {
    return processingRoutingKey;
  }

  public void setProcessingRoutingKey(String processingRoutingKey) {
    this.processingRoutingKey = processingRoutingKey;
  }

  public String getFilesQueue() {
    return filesQueue;
  }

  public void setFilesQueue(String filesQueue) {
    this.filesQueue = filesQueue;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

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

  public String getTokenEndpoint() {
    return tokenEndpoint;
  }

  public void setTokenEndpoint(String tokenEndpoint) {
    this.tokenEndpoint = tokenEndpoint;
  }
}
