package com.example.demo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

	@Autowired
	private PersonRepository repository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value="/person/register", method=RequestMethod.GET) 
	public String registerPerson() {
		return "registerPerson";
	}
	
	@RequestMapping(value = "/people", method = RequestMethod.GET)
	public String listAll(Model model) {
		
		List<Person> list = repository.findAll();
		
		if (list != null) {
			model.addAttribute("people", list);
		} 
		
		return "people";
	}
	
	@RequestMapping(value="/delete/{personId}", method=RequestMethod.GET)
	public String deletePerson(@PathVariable long personId) {
		
		repository.deleteById(personId);
		
		return "redirect:/people";
	}
	
	@RequestMapping(value = "/person/register", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute @Valid  Person person, Errors errors) {
		
		if (errors.hasErrors()) {
			return "registerPerson";
		}
		
		repository.save(person);
		
		return "redirect:/people";
	}
}
