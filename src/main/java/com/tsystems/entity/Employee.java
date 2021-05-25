package com.tsystems.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "{name.null}")
	@Size(min = 2, max = 30, message = "{name.size}")
	private String name;

	@NotNull(message = "{lastname.null}")
	@Size(min = 2, max = 50, message = "{lastname.size}")
	private String lastname;

	@NotNull(message = "{age.null}")
	@Min(value = 0, message = "{age.min}")
	@Max(value = 150, message = "{age.max}")
	private int age;

	@NotNull
	private char gender;

	@ManyToOne
	@JoinColumn(name = "employer_id")
	private Employer employer;

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

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
}
