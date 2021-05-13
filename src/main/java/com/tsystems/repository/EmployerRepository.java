package com.tsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsystems.entity.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
	
	Employer findByUsername(String username);
}
