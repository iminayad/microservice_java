package com.bootcamp.msvcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class MsvcClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcClientApplication.class, args);
	}

}
