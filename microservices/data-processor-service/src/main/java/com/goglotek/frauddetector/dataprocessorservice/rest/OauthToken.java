package com.goglotek.frauddetector.dataprocessorservice.rest;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OauthToken {
    private String token;
    private Date updatedOn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
