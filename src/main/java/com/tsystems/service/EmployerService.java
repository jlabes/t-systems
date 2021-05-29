package com.tsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystems.model.Employer;
import com.tsystems.repository.EmployerRepository;

@Service
public class EmployerService {
	
	@Autowired
	private EmployerRepository repository;
	
	public EmployerService() {
		
	}
	
	public Employer getEmployer(String employerName) {
		
		return repository.findByUsername(employerName);
	}
	
	public Employer getEmployer(long employerId) {
		
		return repository.findById(employerId).get();
	}
}
