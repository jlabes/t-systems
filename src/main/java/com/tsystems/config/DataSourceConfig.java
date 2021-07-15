package com.tsystems.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DataSourceConfig {

	private final String DRIVER_H2 = "org.h2.Driver";
	private final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
	private final String URL_PROD = "jdbc:h2:tcp://localhost:9092/~/Tsystems";
	private final String URL_DEV = "jdbc:mysql://localhost:3306/Tsystems";
	private final String USER = "root";
	private final String PASSWORD = "tsystems123";
	
	@Bean
	@Profile("production")
	public DataSource dataSourceProduction() {
		
		DataSource ds = new DataSource();
		ds.setDriverClassName(DRIVER_H2);
		ds.setUrl(URL_PROD);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		return ds;
	}
	
	@Bean
	@Profile("development")
	public DataSource dataSourceDevelopment() {
		
		DataSource ds = new DataSource();
		ds.setDriverClassName(DRIVER_MYSQL);
		ds.setUrl(URL_DEV);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		return ds;
	}

}
