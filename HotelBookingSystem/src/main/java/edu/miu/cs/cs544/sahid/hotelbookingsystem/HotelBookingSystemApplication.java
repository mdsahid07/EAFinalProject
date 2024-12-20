package edu.miu.cs.cs544.sahid.hotelbookingsystem;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.service.CustomUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HotelBookingSystemApplication {

    private final CustomUserDetailsService customUserDetailsService;
    public HotelBookingSystemApplication(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    public static void main(String[] args) {
        SpringApplication.run(HotelBookingSystemApplication.class, args);
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
//        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authManagerBuilder
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder);
//        return authManagerBuilder.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF using the lambda syntax
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/signup", "/api/auth/signin").permitAll() // Allow public access to signup and signin endpoints
//                        //.requestMatchers("/api/reservations/**").hasRole("USER") // Restrict access to "USER" role
//                        //.requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated() // Protect all other endpoints
//                )
//                .httpBasic(httpBasic -> {}); // Enable HTTP Basic authentication with default settings
//        return http.build();
//    }
}
