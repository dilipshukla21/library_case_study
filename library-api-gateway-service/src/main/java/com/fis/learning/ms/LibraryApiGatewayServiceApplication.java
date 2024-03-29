package com.fis.learning.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class LibraryApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiGatewayServiceApplication.class, args);
	}

}
