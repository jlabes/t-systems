package com.tsystems.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	public Map<Double, List<Employee>> getAllEmployeesGroupingBySalary(String employerName) {

		return getAllEmployees(employerName).stream().collect(Collectors.groupingBy(Employee::getSalary)).entrySet()
				.stream().sorted(Comparator.comparing(Map.Entry::getKey))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public List<Employee> getAllEmployeesActiveOrNotActive(String employerName, boolean isActive) {

		return getAllEmployees(employerName).stream().filter(e -> e.isActive() == isActive)
				.collect(Collectors.toList());
	}

	@Cacheable(value = "employee", key = "#p0")
	public Employee getEmployee(long employeeId) {

		return repository.findById(employeeId).get();
	}

	@Caching(evict = { @CacheEvict(value = "employee", key = "#p0"),
			@CacheEvict(value = "employees", allEntries = true) })
	public void deleteEmployee(long employeeId) {

		repository.deleteById(employeeId);
	}

	@CacheEvict(value = "employees", allEntries = true)
	public void addEmployee(Employee employee) {

		repository.save(employee);
	}

	@Caching(evict = { @CacheEvict(value = "employee", key = "#p0"),
			@CacheEvict(value = "employees", allEntries = true) })
	public void updateEmployee(long employeeId, Employee employee) {

		if (repository.findById(employeeId).get() != null) {

			repository.save(employee);
		}
	}
}
