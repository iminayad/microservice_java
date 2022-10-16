package com.bootcamp.msvcproductredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MsvcProductredisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcProductredisApplication.class, args);
	}

}
