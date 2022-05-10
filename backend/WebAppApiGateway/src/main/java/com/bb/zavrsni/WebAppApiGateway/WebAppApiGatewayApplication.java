package com.bb.zavrsni.WebAppApiGateway;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class WebAppApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApiGatewayApplication.class, args);
	}

	@PreDestroy
	public void shutdownUnirest() {
		Unirest.shutDown();
	}

}
