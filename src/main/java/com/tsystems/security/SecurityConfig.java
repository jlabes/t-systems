package com.tsystems.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tsystems.entity.Employer;
import com.tsystems.repository.EmployerRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private EmployerRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void init(WebSecurity web) throws Exception {

		Employer employer = new Employer();
		employer.setPassword(encoder.encode("123456"));
		employer.setUsername("johnny");
		employer.setRoles(Arrays.asList("ADMIN","USER"));

		repository.save(employer);
		
		employer = new Employer();
		employer.setPassword(encoder.encode("123456"));
		employer.setUsername("luiz");
		employer.setRoles(Arrays.asList("ADMIN","USER"));

		repository.save(employer);

		super.init(web);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				return repository.findByUsername(username);
			}
		});
		
		provider.setPasswordEncoder(encoder);

		auth.authenticationProvider(provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

		http.csrf().disable();
		
		http.headers().frameOptions().disable();
	}

}