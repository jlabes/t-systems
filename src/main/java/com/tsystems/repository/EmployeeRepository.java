package com.tsystems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsystems.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> findAllByEmployerUsername(String employerUsername);
}
