package org.ahmed.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SpringBootApplication
public class Project2Application extends SpringBootServletInitializer{
 
	private static final Logger LOGGER = LoggerFactory.getLogger(Project2Application.class);
	
	public static void main(String[] args) {
		LOGGER.trace("Welcome!");
		SpringApplication.run(Project2Application.class, args);
	}	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Project2Application.class);
	}

}


// ./startup.sh && tail -f ../logs/catalina.out