package com.bunnet.year4.config;

import com.bunnet.year4.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        // Use BCrypt for strong, salted password hashing
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity, enable in production
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to access the home page and API docs
                        .requestMatchers("/", "/home", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // Only users with ROLE_ADMIN can access /admin/**
                        .requestMatchers("admin/**").hasRole("ADMIN")
                        // Users with ROLE_USER or ROLE_ADMIN can access /api/user/**
                        .requestMatchers("user/**").hasAnyRole("USER", "ADMIN")
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.permitAll()) // Use a default login form
                .logout(logout -> logout.permitAll()); // Allow anyone to log out

        return http.build();
    }
}