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

package com.goglotek.frauddetector.dataextractionservice.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.dataextractionservice.configuration.Config;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Not thread safe. For usage in multithreaded environment make the class thread-safe of create new instance of the class for each thread or usage
@Component
public class RestClient {

  @Autowired
  private OauthToken token;

  @Autowired
  private Config config;

  @Autowired
  private ObjectMapper objectMapper;


  private static OkHttpClient client = null;
  private final int MAX_AUTH_RETRY = 10;
  private volatile int reAuthenticationCount = 0;

  public String get(String url, Map<String, String> headersMap) throws Exception {
    if (client == null) {
      client = new OkHttpClient();
    }
    if (headersMap == null) {
      headersMap = new HashMap<>();
    }
    if (headersMap.get("Content-type") == null) {
      headersMap.put("Content-type", "application/json");
    }
    headersMap.put("Authorization", "Bearer " + getToken());

    Request request = new Request.Builder().url(url).get().headers(Headers.of(headersMap)).build();
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      if (hasInvalidOrExpiredTokenError(resp)) {
        //nullify token to force re-authentication
        token = null;
        if (reAuthenticationCount < MAX_AUTH_RETRY) {
          reAuthenticationCount++;
          return get(url, headersMap);
        } else {
          reAuthenticationCount = 0;
          return resp;
        }
      }
      return resp;
    }
  }

  public String get(String url) throws Exception {
    return get(url, null);
  }

  public String put(String url, String data, Map<String, String> headersMap) throws Exception {
    if (client == null) {
      client = new OkHttpClient();
    }
    if (headersMap == null) {
      headersMap = new HashMap<>();
    }
    if (headersMap.get("Content-type") == null) {
      headersMap.put("Content-type", "application/json");
    }
    headersMap.put("Authorization", "Bearer " + getToken());

    RequestBody body = RequestBody.create(data, MediaType.get(headersMap.get("Content-type")));

    Request request = new Request.Builder().url(url).headers(Headers.of(headersMap)).put(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      if (hasInvalidOrExpiredTokenError(resp)) {
        //nullify token to force re-authentication
        token = null;
        if (reAuthenticationCount < MAX_AUTH_RETRY) {
          reAuthenticationCount++;
          return put(url, data, headersMap);
        } else {
          reAuthenticationCount = 0;
          return resp;
        }
      }
      return resp;
    }
  }

  public String put(String url, String data) throws Exception {
    return put(url, data, null);
  }

  public String post(String url, String data, Map<String, String> headersMap,
      boolean isTokenRequest) throws Exception {
    if (client == null) {
      client = new OkHttpClient();
    }
    if (headersMap == null) {
      headersMap = new HashMap<>();
    }
    if (headersMap.get("Content-type") == null) {
      headersMap.put("Content-type", "application/json");
    }
    if (!isTokenRequest) {
      headersMap.put("Authorization", "Bearer " + getToken());
    }

    RequestBody body = RequestBody.create(data, MediaType.get(headersMap.get("Content-type")));

    Request request = new Request.Builder().url(url).headers(Headers.of(headersMap)).post(body)
        .build();

    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      if (hasInvalidOrExpiredTokenError(resp)) {
        //nullify token to force re-authentication
        token = null;
        if (reAuthenticationCount < MAX_AUTH_RETRY) {
          reAuthenticationCount++;
          return post(url, data, headersMap, isTokenRequest);
        } else {
          reAuthenticationCount = 0;
          return resp;
        }
      }
      return resp;
    }
  }

  public String post(String url, String data) throws Exception {
    return post(url, data, null, false);
  }

  boolean hasInvalidOrExpiredTokenError(String response) {
    response = response.toLowerCase();
    if (response.contains("error")) {
      if (response.contains("invalid") || response.contains("expired")) {
        return true;
      }
    }
    return false;
  }

  private String getToken() throws Exception {
    if (token == null || token.getAccessToken() == null || hasTokenExpired()) {
      String tokenResp = getTokenFromServer();
      token = objectMapper.readValue(tokenResp, OauthToken.class);
      return token.getAccessToken();
    } else {
      return token.getAccessToken();
    }
  }

  private boolean hasTokenExpired() {
    //TODO:
    return false;
  }

  private String getTokenFromServer() throws Exception {
    String encoded = "Basic " + Base64.getEncoder()
        .encodeToString((config.getClientId() + ":" + config.getClientSecret()).getBytes());
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", encoded);
    headers.put("Content-type", "application/x-www-form-urlencoded");
    String body = "grant_type=client_credentials&scope=" + config.getScope() + "";
    return post(config.getBaseUrl() + config.getTokenEndpoint(), body, headers, true);
  }
}