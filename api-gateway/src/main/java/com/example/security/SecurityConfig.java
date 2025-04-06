package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // Inject JwtAuthFilter via constructor
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors()
                .and()
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for stateless APIs
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/auth/**").permitAll() // Allow authentication requests without JWT
                        .pathMatchers("/api/test/**").permitAll() // Allow other public endpoints
                        .anyExchange().permitAll() // Secure all other routes
                )
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION); // Add JWT filter

        return http.build();
    }
}
