package com.goglotek.frauddetector.dataextractionservice.datastoreservice.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.goglotek.frauddetector.datastoreservice.repository")
public class JpaConfig {

}
