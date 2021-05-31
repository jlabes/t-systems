package com.tsystems.controller;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsystems.model.Employee;
import com.tsystems.model.Employer;
import com.tsystems.service.EmployeeService;
import com.tsystems.service.EmployerService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@Autowired
	private EmployerService otherService;

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

		model.addAttribute("employee", new Employee());

		return "employee_register";
	}

	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
	public String showEmployeeProfile(@PathVariable long employeeId, Model model) {

		model.addAttribute("employee", service.getEmployee(employeeId));

		return "employee_register";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.POST)
	public String addOrUpdateEmployee(@ModelAttribute("employee") @Valid Employee employee, Errors errors,
			Authentication authentication) {

		if (errors.hasErrors()) {

			return "employee_register";
		}

		employee = addEmployerToEmployee(employee, authentication);

		if (employee.getId() == null) {

			service.addEmployee(employee);
		} else {

			service.updateEmployee(employee.getId(), employee);
		}

		return "redirect:/employees";
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String listAllEmployees(Model model, Authentication authentication) {

		List<Employee> employees = service.getAllEmployees(authentication.getName());

		if (employees != null) {

			model.addAttribute("employees", employees);
		}

		return "employees";
	}

	@RequestMapping(value = "/employees/condition", method = RequestMethod.GET)
	public String listAllEmployeesActiveOrNotActive(@RequestParam("active") boolean active, Model model,
			Authentication authentication) {

		List<Employee> employees = service.getAllEmployeesActiveOrNotActive(authentication.getName(), active);

		if (employees != null) {

			model.addAttribute("employees", employees);
		}

		return "employees";
	}

	@RequestMapping(value = "/employees/salary", method = RequestMethod.GET)
	public String listAllEmployeesSalary(Model model, Authentication authentication) {

		List<Employee> employees = service.getAllEmployees(authentication.getName());

		model.addAttribute("employees", employees);

		return "employees_salary";
	}

	@RequestMapping(value = "/employees/salary/group", method = RequestMethod.GET)
	public String listAllEmployeesGroupingBySalary(Model model, Authentication authentication) {

		Map<Double, List<Employee>> employees = service.getAllEmployeesGroupingBySalary(authentication.getName());

		if (employees != null) {

			model.addAttribute("employees_group", employees);
		}

		return "employees_salary";
	}

	@RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable long employeeId) {

		service.deleteEmployee(employeeId);

		return "redirect:/employees";
	}

	private Employee addEmployerToEmployee(Employee employee, Authentication authentication) {

		Employer employer = otherService.getEmployer(authentication.getName());

		employee.setEmployer(employer);

		return employee;
	}
}
