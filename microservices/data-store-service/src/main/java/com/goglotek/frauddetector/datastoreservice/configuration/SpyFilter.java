package com.goglotek.frauddetector.datastoreservice.configuration;

import com.goglotek.frauddetector.datastoreservice.service.impl.FilesServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Iterator;

public class SpyFilter extends OncePerRequestFilter {
    Logger logger = LogManager.getLogger(SpyFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("spying on request...");
        for (Iterator<String> it = request.getHeaderNames().asIterator(); it.hasNext(); ) {
            String s = it.next();
            logger.info(s + ":" + request.getHeader(s));
        }
        logger.info("authentication type:" + request.getAuthType());
        logger.info("Context path" + request.getContextPath());

        filterChain.doFilter(request, response);
    }
}
