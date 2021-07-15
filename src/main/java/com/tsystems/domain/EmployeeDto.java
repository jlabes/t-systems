package com.tsystems.domain;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EmployeeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(min = 2, max = 30, message = "{name.size}")
	@NotEmpty(message = "{name.null}")
	private String name;

	@Size(min = 2, max = 50, message = "{lastname.size}")
	@NotEmpty(message = "{lastname.null}")
	private String lastname;

	@Min(value = 0, message = "{age.min}")
	@Max(value = 150, message = "{age.max}")
	private int age;

	private char gender;

	@Min(value = 0, message = "{salary.min}")
	private double salary;

	private boolean active;

	public EmployeeDto() {

	}

	public EmployeeDto(String name, String lastname, int age, char gender, double salary, boolean active) {
		this.name = name;
		this.lastname = lastname;
		this.age = age;
		this.gender = gender;
		this.salary = salary;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
