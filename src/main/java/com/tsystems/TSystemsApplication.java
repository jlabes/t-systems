package com.tsystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tsystems")
public class TSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TSystemsApplication.class, args);
	}
}
