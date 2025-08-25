package com.goglotek.frauddetector.datastoreservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {
    @GetMapping(value = "/authentication/redirect/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String handleCode(@RequestParam String code) {
        return "{\"authorization_code\": \"" + code + "\"}";
    }
}

