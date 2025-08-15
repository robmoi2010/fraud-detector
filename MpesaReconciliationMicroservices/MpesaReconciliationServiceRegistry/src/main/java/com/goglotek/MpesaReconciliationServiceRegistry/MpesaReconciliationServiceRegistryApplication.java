package com.goglotek.MpesaReconciliationServiceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MpesaReconciliationServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpesaReconciliationServiceRegistryApplication.class, args);
	}

}
