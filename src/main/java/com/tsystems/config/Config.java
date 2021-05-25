package com.tsystems.config;

import java.sql.SQLException;
import java.util.Locale;

import org.h2.tools.Server;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class Config implements WebMvcConfigurer {
	
	@Bean
    public PasswordEncoder passwordEncoder(){
		
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public MessageSource messageSource() {
		
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();	    
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    
	    return messageSource;
	}
	
	@Bean
	public Validator getValidator() {
		
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    
	    return bean;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		
		return localeResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		
		return interceptor;
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(localeChangeInterceptor());
	}
}
