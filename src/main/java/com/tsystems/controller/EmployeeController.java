package com.tsystems.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsystems.model.Employee;
import com.tsystems.model.EmployeeDto;
import com.tsystems.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@Autowired
	private ModelMapper mapper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		return "index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String changeLanguage(@RequestParam("lang") String lang, Model model) {

		model.addAttribute("lang", lang);

		return "index";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.GET)
	public String registerEmployee(Model model) {

		model.addAttribute("employee", new EmployeeDto());

		return "employee_register";
	}

	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
	public String showEmployeeProfile(@PathVariable long employeeId, Model model) {

		model.addAttribute("employee", service.getEmployee(employeeId));

		return "employee_register";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.POST)
	public String addOrUpdateEmployee(@Valid EmployeeDto dto, Errors errors, Authentication auth) {

		Employee employee = convertToEntity(dto);

		if (errors.hasErrors()) {

			return "employee_register";
		}

		if (employee.getId() == null) {

			service.addEmployee(employee, auth.getName());
		} else {

			service.updateEmployee(employee.getId(), employee);
		}

		return "redirect:/employees";
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String listAllEmployees(Model model, Authentication auth) {

		model.addAttribute("employees", service.getAllEmployees(auth.getName()));

		return "employees";
	}

	@RequestMapping(value = "/employees/condition", method = RequestMethod.GET)
	public String listAllEmployeesActiveOrNotActive(@RequestParam("active") boolean active, Model model,
			Authentication auth) {

		model.addAttribute("employees", service.getAllEmployeesActiveOrNotActive(auth.getName(), active));

		return "employees";
	}

	@RequestMapping(value = "/employees/salary", method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	public String listAllEmployeesSalary(Model model, Authentication auth) {

		model.addAttribute("employees", service.getAllEmployees(auth.getName()));

		return "employees_salary";
	}

	@RequestMapping(value = "/employees/salary/group", method = RequestMethod.GET)
	public String listAllEmployeesGroupingBySalary(Model model, Authentication auth) {
		
		model.addAttribute("employees_group", service.getAllEmployeesGroupingBySalary(auth.getName()));

		return "employees_salary";
	}

	@RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable long employeeId) {

		service.deleteEmployee(employeeId);

		return "redirect:/employees";
	}

	private Employee convertToEntity(EmployeeDto dto) {

		return mapper.map(dto, Employee.class);
	}
}
