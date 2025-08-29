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

package com.goglotek.frauddetector.datastoreservice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goglotek.frauddetector.datastoreservice.configuration.Config;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    MvcResult authResult = mvc.perform(
            get("/oauth2/authorize?response_type=code&client_id=" + clientId + "&redirect_uri="
                + redirectUrl)
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
