package com.tsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsystems.domain.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
	
	Employer findByUsername(String username);
	
	Employer findByToken(String confirmationToken);
	
	Employer findByEmail(String email);
}
