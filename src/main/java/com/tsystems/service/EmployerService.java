package com.tsystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tsystems.model.Employer;
import com.tsystems.repository.EmployerRepository;

@Service
public class EmployerService {
	
	@Autowired
	private EmployerRepository repository;
	
	public EmployerService() {
		
	}
	
	@Cacheable(value = "employer", key = "#p0")
	public Employer getEmployer(String employerName) {
		
		return repository.findByUsername(employerName);
	}
	
	public Employer getEmployer(long employerId) {
		
		return repository.findById(employerId).get();
	}
	
	public List<Employer> getEmployers() {
		
		return repository.findAll();
	}
	
	public void addEmployer(Employer employer) {
		
		repository.save(employer);
	}
}
