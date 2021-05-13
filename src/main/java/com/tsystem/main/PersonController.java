package com.tsystem.main;

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

@Controller
public class PersonController {

	@Autowired
	private PersonRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		return "index";
	}

	@RequestMapping(value = "/person/register", method = RequestMethod.GET)
	public String registerPerson(Model model) {
		model.addAttribute("person", new Person());
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

	@RequestMapping(value = "/delete/{personId}", method = RequestMethod.GET)
	public String deletePerson(@PathVariable long personId) {

		repository.deleteById(personId);

		return "redirect:/people";
	}

	@RequestMapping(value = "/person/register", method = RequestMethod.POST)
	public String addPerson(@Valid Person person, Errors errors, RedirectAttributes model) {

		if (errors.hasErrors()) {
			return "registerPerson";
		}

		repository.save(person);
		
		model.addFlashAttribute("person", person);
		model.addAttribute("personId", person.getId());

		return "redirect:/profile/{personId}";
	}

	@RequestMapping(value = "/profile/{personId}", method = RequestMethod.GET)
	public String showPersonProfile(@PathVariable long personId, Model model) {

		if (!model.containsAttribute("person")) {

			model.addAttribute("person", repository.getOne(personId));
		}		
		
		return "profile";
	}
}
