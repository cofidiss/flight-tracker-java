package com.flight.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/signup", "/api/flight/live/filter","/swagger-ui/**").permitAll() // Allow these paths to everyone
                        .anyRequest().authenticated() // Require authentication for other requests
                )
                .csrf().disable() // Disable CSRF protection
                .formLogin().disable(); // Disable form login
        */
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Allow all requests
                )
                .csrf().disable() // Disable CSRF protection
                .formLogin().disable();
        return http.build();
    }
}
