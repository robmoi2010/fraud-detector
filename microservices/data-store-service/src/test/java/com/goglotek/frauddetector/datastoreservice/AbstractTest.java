package com.goglotek.frauddetector.datastoreservice;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractTest {
    @Autowired
    protected MockMvc mvc;
    protected String token;

    @LocalServerPort
    protected String serverPort;

    @Autowired
    protected Config config;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String tokenUrl = "";
    protected String baseUrl = "";

    @BeforeEach
    protected void startUp() throws Exception {
        tokenUrl = "http://localhost:" + serverPort + "/oauth2/token";
        baseUrl = "http://localhost:" + serverPort + "";
        shouldLogInAndReturnToken();
    }

    private void shouldLogInAndReturnToken() throws Exception {
        //create request headers and content
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Arrays.asList("Basic dGVzdDp0ZXN0MTIzNDU="));
        headers.put("Content-Type", Arrays.asList("application/x-www-form-urlencoded"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.putAll(headers);

        StringBuilder sb = new StringBuilder();
        sb.append("grant_type").append("=").append("client_credentials");

        //send request
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(tokenUrl).headers(httpHeaders).content(sb.toString())).andReturn();
        assertTrue(mvcResult != null);
        String results = mvcResult.getResponse().getContentAsString();

        //confirm that results is not null or empty
        assertTrue(results != null && !results.isEmpty());

        //get access token from results
        JsonNode node = objectMapper.readTree(results);
        token = node.get("access_token").asText();
        assertTrue(token != null && !token.isEmpty());
    }

}
