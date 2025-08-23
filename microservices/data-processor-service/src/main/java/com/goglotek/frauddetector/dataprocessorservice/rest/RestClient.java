package com.goglotek.frauddetector.dataprocessorservice.rest;

import com.goglotek.frauddetector.dataprocessorservice.configuration.Config;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestClient {
    @Autowired
    private OauthToken token;
    @Autowired
    private Config config;

    private static OkHttpClient client = null;
    public static final MediaType JSON = MediaType.get("application/json");

    public String get(String url, Map<String, String> headersMap) throws Exception {
        if (client == null) {
            client = new OkHttpClient();
        }
        if (headersMap == null) {
            headersMap = new HashMap<>();
        }
        headersMap.put("Content-type", "application/json");
        headersMap.put("Authorization", "Bearer " + getToken());

        Request request = new Request.Builder().url(url).get().headers(Headers.of(headersMap)).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
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
        headersMap.put("Content-type", "application/json");
        headersMap.put("Authorization", "Bearer " + getToken());

        RequestBody body = RequestBody.create(data, JSON);

        Request request = new Request.Builder().url(url).headers(Headers.of(headersMap)).put(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String put(String url, String data) throws Exception {
        return put(url, data, null);
    }

    public String post(String url, String data, Map<String, String> headersMap) throws Exception {
        if (client == null) {
            client = new OkHttpClient();
        }
        if (headersMap == null) {
            headersMap = new HashMap<>();
        }
        headersMap.put("Content-type", "application/json");
        headersMap.put("Authorization", "Bearer " + getToken());

        RequestBody body = RequestBody.create(data, JSON);

        Request request = new Request.Builder().url(url).headers(Headers.of(headersMap)).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            //check for expired token response and refresh token
            return resp;
        }
    }

    public String post(String url, String data) throws Exception {
        return post(url, data, null);
    }

    private String getToken() throws Exception {
        if (token.getToken() == null) {
            token.setToken(getTokenFromServer());
            token.setUpdatedOn(new Date());
            return token.getToken();
        } else {
            return token.getToken();
        }
    }

    private String getTokenFromServer() throws Exception {
        String encoded = "Basic " + Base64.getEncoder().encode((config.getOauthId() + ":" + config.getOauthSecret()).getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", encoded);
        String body = "grant_type=password&username=" + config.getWsUsername() + "&password=" + config.getWsPassword();
        return post(config.getBaseUrl() + "oauth/token", body, headers);
    }
}