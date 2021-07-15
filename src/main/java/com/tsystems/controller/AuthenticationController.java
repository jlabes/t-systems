package com.tsystems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsystems.domain.Employer;
import com.tsystems.domain.EmployerDto;
import com.tsystems.service.EmailService;
import com.tsystems.service.EmployerService;

@Controller
public class AuthenticationController {

	@Autowired
	private EmployerService employerService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {

		return "login";
	}

	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String showLoginErrorPage(Model model) {

		model.addAttribute("error", true);

		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showEmployerRegisterPage(Model model) {

		model.addAttribute("user", new EmployerDto());

		return "user_register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerEmployer(@Valid EmployerDto employerDto, BindingResult erros, HttpServletRequest request) {

		if (erros.hasErrors())
			return "user_register";

		if (employerService.isEmailRegistered(employerDto.getEmail())) {

			erros.reject("email");

			return "user_register";
		}

		Employer employer = employerService.saveEmployer(employerDto);

		emailService.sendEmail(employer.getEmail(), request.getScheme(), request.getServerName(),
				request.getServerPort(), request.getContextPath(), employer.getToken());

		return "redirect:/login";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirmToken(@RequestParam("token") String token, Model model) {

		if (!employerService.isMatchingToken(token)) {

			model.addAttribute("error", true);

			return "error";
		}

		employerService.employerConfirmed(token);
	
		return "redirect:login";
	}

}
