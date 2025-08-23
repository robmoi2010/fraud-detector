package com.goglotek.frauddetector.datastoreservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {
    @GetMapping("/authentication/redirect/code")
    public String handleCode(@RequestParam String code) {
        return "Authorization code received: " + code;
    }
}

