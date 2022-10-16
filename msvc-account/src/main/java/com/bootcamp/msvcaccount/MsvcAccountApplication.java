package com.bootcamp.msvcaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsvcAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAccountApplication.class, args);
	}

}
