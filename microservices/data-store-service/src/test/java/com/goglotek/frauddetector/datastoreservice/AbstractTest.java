package com.goglotek.frauddetector.datastoreservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.jayway.jsonpath.JsonPath;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractTest {
    @Autowired
    protected MockMvc mvc;

    protected String token;
    protected String tokenWithoutRoles;

    @Autowired
    protected Config config;

    protected static String clientId = "test";
    protected static String clientSecret = "test12345";
    protected static String userId = "user1@gmail.com";
    protected static String redirectUrl = "http://localhost:8080/authentication/redirect/code";

    @BeforeAll
    protected void startUp() throws Exception {
        shouldLogInAndUpdateToken();
    }

    private void shouldLogInAndUpdateToken() throws Exception {
        token = getToken("USER");
        tokenWithoutRoles = getToken("");
    }

    private String getToken(String... roles) throws Exception {
        MvcResult authResult = mvc.perform(get("/oauth2/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl)
                        .with(httpBasic(clientId, clientSecret))
                        .with(user(userId).roles(roles)))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        String redirect = authResult.getResponse().getRedirectedUrl();

        assertTrue(redirect != null && !redirect.isEmpty());

        String code = redirect.split("code=")[1];

        assertTrue(code != null && !code.isEmpty());

        // Exchange code for token
        MvcResult tokenResult = mvc.perform(post("/oauth2/token")
                        .param("grant_type", "authorization_code")
                        .param("code", code)
                        .param("redirect_uri", redirectUrl)
                        .with(httpBasic(clientId, clientSecret)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andReturn();

        String accessToken = JsonPath.read(
                tokenResult.getResponse().getContentAsString(),
                "$.access_token"
        );

        assertTrue(!accessToken.isEmpty());
        return accessToken;
    }

}
