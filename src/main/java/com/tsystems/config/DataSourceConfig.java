package com.tsystems.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DataSourceConfig {

	private final String DRIVER = "org.h2.Driver";
	private final String URL_PROD = "jdbc:h2:tcp://localhost:9092/~/tsystems";
	private final String URL_DEV = "jdbc:h2:mem:dbtsystem";
	private final String USER = "johnny";
	private final String PASSWORD = "";
	
	/**
	 * java -cp h2-1.4.199.jar org.h2.tools.Server
	 * @return DataSource
	 */
	@Bean
	@Profile("production")
	public DataSource dataSourceProduction() {
		
		DataSource ds = new DataSource();
		ds.setDriverClassName(DRIVER);
		ds.setUrl(URL_PROD);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		return ds;
	}
	
	@Bean
	@Profile("development")
	public DataSource dataSourceDevelopment() {
		
		DataSource ds = new DataSource();
		ds.setDriverClassName(DRIVER);
		ds.setUrl(URL_DEV);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		return ds;
	}

}
