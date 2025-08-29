package com.goglotek.frauddetector.datastoreservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private Config config;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // LocalDate
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(config.getInternalTimestampFormat().split(Pattern.quote(" "))[0]));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(config.getInternalTimestampFormat()));
        registrar.registerFormatters(registry);
    }
}

