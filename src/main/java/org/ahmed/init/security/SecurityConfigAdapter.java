package org.ahmed.init.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigAdapter implements WebMvcConfigurer {


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests()
			.requestMatchers("/api", "/v1", "/employees")
			.hasAnyRole("ADMIN").anyRequest()
			.authenticated().and()
			.httpBasic().and()
			.cors().and()
			.csrf().disable();
		return http.build();
	}

	@Bean
	protected InMemoryUserDetailsManager configAuthentication() {

		List<UserDetails> users = new ArrayList<>();
		List<GrantedAuthority> adminAuthority = new ArrayList<>();
		adminAuthority.add(new SimpleGrantedAuthority("ADMIN"));
		UserDetails admin = new User("devs", "{noop}password", adminAuthority);
		users.add(admin);
		return new InMemoryUserDetailsManager(users);
	}

}
