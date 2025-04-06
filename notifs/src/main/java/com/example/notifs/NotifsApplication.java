package com.example.notifs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories("com.example.notifs.repository") // Adjust if needed
@EntityScan("com.example.notifs.entity")
public class NotifsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifsApplication.class, args);
	}

}
