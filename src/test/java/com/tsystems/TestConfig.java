package com.tsystems;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;

import com.tsystems.domain.Employer;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfig {

	@Bean
	public Employer createEmployer() {
		return new Employer(1L, "Tsystems", "example@t-systems.com", "password", null, true, Arrays.asList("USER"));
	}

}
