package org.ahmed.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class Project2Application extends SpringBootServletInitializer{

	
	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Project2Application.class);
	}

}

