package com.tsystems.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsystems.domain.EmployeeDto;
import com.tsystems.domain.Employer;
import com.tsystems.domain.User;
import com.tsystems.service.EmployeeService;

@Controller
public class MainController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHomePage() {

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

		model.addAttribute("employee", employeeService.getEmployee(employeeId));

		return "employee_register";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.POST)
	public String saveOrUpdateEmployee(@Valid @RequestBody EmployeeDto employeeDto, BindingResult errors, HttpSession session) {
		
		if (errors.hasErrors()) {

			return "employee_register";
		}

		employeeService.saveEmployee(employeeDto, getEmployerFromUser(session.getAttribute("currentUser")));

		return "redirect:/employees";
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String listAllEmployees(Model model) {

		model.addAttribute("employees", employeeService.getAllEmployees(null));

		return "employees";
	}

	@RequestMapping(value = "/employees/condition", method = RequestMethod.GET)
	public String listAllEmployeesActiveOrNotActive(@RequestParam("active") boolean active, Model model) {

		model.addAttribute("employees", employeeService.getAllEmployeesActiveOrNotActive(null, active));

		return "employees";
	}

	@RequestMapping(value = "/employees/salary", method = RequestMethod.GET)
	public String listAllEmployeesSalary(Model model) {

		model.addAttribute("employees", employeeService.getAllEmployees(null));

		return "employees_salary";
	}

	@RequestMapping(value = "/employees/salary/group", method = RequestMethod.GET)
	public String listAllEmployeesGroupingBySalary(Model model) {
		
		model.addAttribute("employees_group", employeeService.getAllEmployeesGroupingBySalary(null));

		return "employees_salary";
	}

	@RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable long employeeId) {

		employeeService.deleteEmployee(employeeId);

		return "redirect:/employees";
	}

	private Employer getEmployerFromUser(Object object) {
		
		if (!(object instanceof User))
			throw new RuntimeException("No user found!");
			
		return ((User)object).getEmployer();
	}
}
