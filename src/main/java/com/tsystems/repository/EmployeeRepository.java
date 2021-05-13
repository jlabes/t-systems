package com.tsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsystems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
}
