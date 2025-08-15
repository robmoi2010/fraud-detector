package com.goglotek.mpesareconciliation.ftpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MpesaReconciliationFtpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpesaReconciliationFtpServiceApplication.class, args);
	}

}
