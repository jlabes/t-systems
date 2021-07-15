package com.tsystems.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tsystems.domain.Employer;
import com.tsystems.domain.EmployerDto;
import com.tsystems.repository.EmployerRepository;

@Service
public class EmployerService {
	
	@Autowired
	private EmployerRepository repository;
	
	public EmployerService() {
		
	}
	
	public Employer getEmployer(String name) {
		
		return repository.findByUsername(name);
	}
	
	public boolean isEmailRegistered(String email) {
		
		return repository.findByEmail(email) == null ? false : true;
	}
	
	public boolean isMatchingToken(String token) {
		
		return repository.findByToken(token) == null ? false : true;
	}
	
	public Employer getEmployerByToken(String token) {
		
		return repository.findByToken(token);
	}
	
	public Employer getEmployer(long id) {
		
		return repository.findById(id).get();
	}
	
	public List<Employer> getEmployers() {
		
		return repository.findAll();
	}
	
	public void saveEmployer(Employer employer) {
		
		repository.save(employer);
	}
	
	public Employer saveEmployer(EmployerDto emploterDto) {
		
		Employer employer = convertFromDtoToEntity(emploterDto);
		employer.setToken(UUID.randomUUID().toString());
		employer.setPassword(new BCryptPasswordEncoder().encode(employer.getPassword()));
		employer.setRoles(Arrays.asList("USER"));
		
		saveEmployer(employer);
		
		return employer;
	}
	
	public void employerConfirmed(String token) {
		
		Employer employer = getEmployerByToken(token);
		employer.setEnabled(true);
		
		saveEmployer(employer);
	}
	
	private Employer convertFromDtoToEntity(EmployerDto employerDto) {

		return new Employer(employerDto.getId(), employerDto.getUsername(), employerDto.getEmail(), employerDto.getPassword(), null, false, null);
	}
}
