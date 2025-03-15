package com.flight.config;

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
                        .requestMatchers("/login", "/signup", "/api/flight/live/filter","/swagger-ui/**").permitAll() // Bu yolları herkese aç
                        .anyRequest().authenticated() // Diğer istekleri kimlik doğrulama gerektir
                )
                .csrf().disable() // CSRF korumasını devre dışı bırak
                .formLogin().disable(); // Fo
        */
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Tüm isteklere izin ver
                )
                .csrf().disable() // CSRF korumasını kapat
                .formLogin().disable();
        return http.build();
    }
}
