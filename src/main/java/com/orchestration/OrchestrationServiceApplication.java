package com.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.orchestration.routingService")
@EnableDiscoveryClient
@EnableScheduling
public class OrchestrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServiceApplication.class, args);
	}

}
