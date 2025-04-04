package com.example.chatapp_v2;

import com.example.chatapp_v2.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatappV2Application {

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	public static void main(String[] args) {
		SpringApplication.run(ChatappV2Application.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtAuthFilter> jwtFilter() {
		FilterRegistrationBean<JwtAuthFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new JwtAuthFilter(jwtSecret));
		filter.addUrlPatterns("/*");
		return filter;
	}

}
