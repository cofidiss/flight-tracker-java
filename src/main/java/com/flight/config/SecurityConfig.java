package com.flight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Tüm URL'lere izin ver
                )
                .csrf().disable() // CSRF korumasını devre dışı bırak (isteğe bağlı)
                .formLogin().disable(); // Login formunu devre dışı bırak (isteğe bağlı)

        return http.build();
    }
}
