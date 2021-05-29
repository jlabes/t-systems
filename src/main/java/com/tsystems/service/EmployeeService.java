package com.tsystems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.tsystems.model.Employee;
import com.tsystems.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;
	
	public EmployeeService() {
		
	}
	
	@Cacheable(value = "employees")
	public List<Employee> getAllEmployees(String employerName) {
		
		return repository.findAllByEmployerUsername(employerName);
	}
	
	@Cacheable(value="employee", key="#p0")
	public Employee getEmployee(long employeeId) {
		
		return repository.findById(employeeId).get();
	}
	
	@Caching(evict = {
			@CacheEvict(value="employee", key = "#p0"),
			@CacheEvict(value="employees", allEntries = true)
	})
	public void deleteEmployee(long employeeId) {
		
		repository.deleteById(employeeId);
	}
	
	@CacheEvict(value="employees", allEntries = true)
	public void addEmployee(Employee employee) {
		
		repository.save(employee);
	}
	
	@Caching(evict = {
			@CacheEvict(value="employee", key = "#p0"),
			@CacheEvict(value="employees", allEntries = true)
	})
	public void updateEmployee(long employeeId, Employee employee) {
		
		if(repository.findById(employeeId).get() != null) {
			
			repository.save(employee);
		}
	}
}
