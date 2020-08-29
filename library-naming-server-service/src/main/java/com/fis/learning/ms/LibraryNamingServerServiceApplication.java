package com.fis.learning.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LibraryNamingServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryNamingServerServiceApplication.class, args);
	}

}
