package com.tsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	private final String SUBJECT = "Token validation";
	
	private final String URL = "%1$s://%2$s:%3$d%4$s/confirm?token=%5$s";
	
	@Async
	public void sendEmail(String to, String subject, String message) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(to);
		email.setSubject(subject);
		email.setText(message);
		
		mailSender.send(email);
	}
	
	public void sendEmail(String email, String scheme, String server, int port, String context, String token) {
		
		sendEmail(email, SUBJECT, String.format(URL, scheme, server, port, context, token));
	}
}
