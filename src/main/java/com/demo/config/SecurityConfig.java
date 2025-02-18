package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Optional: Disable CSRF if you're not dealing with forms
                .authorizeRequests()
                .anyRequest().permitAll() // Allow all requests without authentication
                .and()
                .formLogin().disable();  // Disable form login (optional)

        return http.build();
    }
}
