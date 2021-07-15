package com.tsystems.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Employer employer;
	
	public User() {
		
	}
	
	public User(Employer employer) {
		this.employer = employer;
	}
	
	public Employer getEmployer() {
		return employer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return employer.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return employer.getPassword();
	}

	@Override
	public String getUsername() {
		return employer.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return employer.isEnabled();
	}
}
