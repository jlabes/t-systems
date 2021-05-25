package com.tsystems;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tsystems.entity.Employer;
import com.tsystems.repository.EmployerRepository;

@SpringBootApplication
public class TSystemsApplication {

	@Autowired
	private EmployerRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TSystemsApplication.class, args);
	}

	@PostConstruct
	public void addEmployers() {
		repository.save(new Employer("johnny", encoder.encode("tsystems"), Arrays.asList("ADMIN", "USER")));
		repository.save(new Employer("gislaine", encoder.encode("tsystems"), Arrays.asList("ADMIN", "USER")));
	}

}
