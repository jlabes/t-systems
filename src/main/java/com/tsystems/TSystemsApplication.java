package com.tsystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TSystemsApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(TSystemsApplication.class, args);
	}

}
