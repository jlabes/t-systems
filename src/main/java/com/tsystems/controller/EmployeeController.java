package com.tsystems.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tsystems.entity.Employee;
import com.tsystems.entity.Employer;
import com.tsystems.repository.EmployeeRepository;
import com.tsystems.repository.EmployerRepository;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployerRepository employerRepository;

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
		
		return "registerEmployee";
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String listAllEmployees(Model model, Authentication authentication) {

		List<Employee> employeeList = employeeRepository.findAllByEmployerUsername(authentication.getName());

		if (employeeList != null) {
			
			model.addAttribute("employees", employeeList);
		}

		return "employees";
	}

	@RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable long employeeId) {

		employeeRepository.deleteById(employeeId);

		return "redirect:/employees";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.POST)
	public String addEmployee(@Valid Employee employee, Errors errors, RedirectAttributes model, Authentication authentication) {

		if (errors.hasErrors()) {
			
			return "registerPerson";
		}
		
		employeeRepository.save(addEmployerToEmployee(employee, authentication));
		
		model.addFlashAttribute("employee", employee);
		
		model.addAttribute("employeeId", employee.getId());

		return "redirect:/profile/{employeeId}";
	}

	@RequestMapping(value = "/profile/{employeeId}", method = RequestMethod.GET)
	public String showEmployeeProfile(@PathVariable long employeeId, Model model) {

		if (!model.containsAttribute("employee")) {

			model.addAttribute("employee", employeeRepository.getOne(employeeId));
		}		
		
		return "profile";
	}
	
	private Employee addEmployerToEmployee(Employee employee, Authentication authentication) {
		
		Employer employer = employerRepository.findByUsername(authentication.getName());
		
		employee.setEmployer(employer);
		
		return employee;
	}
}
