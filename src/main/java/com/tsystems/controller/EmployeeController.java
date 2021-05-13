package com.tsystems.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tsystems.entity.Employee;
import com.tsystems.entity.Employer;
import com.tsystems.repository.EmployeeRepository;
import com.tsystems.repository.EmployerRepository;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private EmployerRepository repoTemp;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		return "index";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.GET)
	public String registerEmployee(Model model) {
		
		model.addAttribute("employee", new Employee());
		
		return "registerEmployee";
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String listAllEmployees(Model model) {

		List<Employee> employeeList = repository.findAll();

		if (employeeList != null) {
			
			model.addAttribute("employees", employeeList);
		}

		return "employees";
	}

	@RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable long employeeId) {

		repository.deleteById(employeeId);

		return "redirect:/employees";
	}

	@RequestMapping(value = "/employee/register", method = RequestMethod.POST)
	public String addEmployee(@Valid Employee employee, Errors errors, RedirectAttributes model) {

		if (errors.hasErrors()) {
			
			return "registerPerson";
		}

		//teste
		Employer employer = repoTemp.getOne(1l);
		
		employee.setEmployer(employer);
		
		repository.save(employee);
		
		model.addFlashAttribute("employee", employee);
		
		model.addAttribute("employeeId", employee.getId());

		return "redirect:/profile/{employeeId}";
	}

	@RequestMapping(value = "/profile/{employeeId}", method = RequestMethod.GET)
	public String showEmployeeProfile(@PathVariable long employeeId, Model model) {

		if (!model.containsAttribute("employee")) {

			model.addAttribute("employee", repository.getOne(employeeId));
		}		
		
		return "profile";
	}
}
