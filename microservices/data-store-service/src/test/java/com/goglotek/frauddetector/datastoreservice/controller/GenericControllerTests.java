package com.goglotek.frauddetector.datastoreservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.goglotek.frauddetector.datastoreservice.AbstractTest;

public class GenericControllerTests extends AbstractTest {
    @BeforeEach
    public void setUp() throws Exception {
        super.startUp();
    }

    @Test
    public void latestTest() throws Exception {
        String uri = "/api/latest";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }
}
