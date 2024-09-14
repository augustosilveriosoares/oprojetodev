package com.opd.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerOpdApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerOpdApplication.class, args);
	}

}
