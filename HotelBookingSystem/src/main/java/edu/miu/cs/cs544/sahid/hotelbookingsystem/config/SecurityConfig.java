package edu.miu.cs.cs544.sahid.hotelbookingsystem.config;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/signup", "/api/auth/signin").permitAll() // Public endpoints
//                        .requestMatchers("/api/reservations/**").hasRole("USER") // Restricted to ROLE_USER
//                        .requestMatchers("/api/hotels").permitAll()
//                        .requestMatchers("/api/hotels/**").hasRole("ADMIN")
//                        .requestMatchers("/api/rooms").permitAll()
//                        .requestMatchers("/api/rooms/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(httpBasic -> {});
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .authorizeHttpRequests(auth -> auth
                    // Use AntPathRequestMatcher for non-MVC patterns
                    .requestMatchers(new AntPathRequestMatcher("/api/auth/signup")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/auth/signin")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/hotels")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/reservations/**")).hasRole("USER")
                    .requestMatchers(new AntPathRequestMatcher("/api/hotels/**")).hasRole("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/api/rooms")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/rooms/**")).hasRole("ADMIN")
                    .anyRequest().authenticated() // Any other endpoint requires authentication
            )
            .httpBasic(httpBasic -> {}); // Basic Authentication
    return http.build();
}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
