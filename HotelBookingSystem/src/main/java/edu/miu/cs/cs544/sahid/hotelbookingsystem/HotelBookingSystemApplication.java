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

}
