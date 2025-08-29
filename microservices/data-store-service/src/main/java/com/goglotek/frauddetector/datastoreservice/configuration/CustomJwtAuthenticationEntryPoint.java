package com.goglotek.frauddetector.datastoreservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    static final Logger logger = LogManager.getLogger(CustomJwtAuthenticationEntryPoint.class);
    @Autowired
    Config config;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String token = request.getHeader("Authorization");
        token = token.substring(7);
        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        // JWT Claims (payload)
        String claims = "";
        try {
            claims = signedJWT.getJWTClaimsSet().toJSONObject().toString();
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        if (claims.contains("scope")) {
            //client_credentials grant_type
            if (claims.contains(config.getMachineRoleName())) {
                final Map<String, Object> errorDetails = new HashMap<>();
                if (authException.getCause() instanceof com.nimbusds.jwt.proc.BadJWTException &&
                        authException.getCause().getMessage().contains("Expired JWT")) {
                    errorDetails.put("error", "Expired JWT Token");
                    errorDetails.put("message", "The provided authentication token is Expired.");
                }
                if (authException.getCause() instanceof JwtException) {
                    errorDetails.put("error", "Invalid JWT Token");
                    errorDetails.put("message", "The provided authentication token is invalid or malformed.");
                } else {
                    errorDetails.put("error", "Authentication Failed");
                    errorDetails.put("message", authException.getMessage());
                }
                new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
                return;
            }
        }
        //default, redirect to login for authorization_code grant type
        response.sendRedirect("/login");
    }
}