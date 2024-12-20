package edu.miu.cs.cs544.sahid.hotelbookingsystem.controller;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.User;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.UserRespository;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRole("ROLE_USER"); // Default role
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin() {
        return ResponseEntity.ok("Sign in successful!");
    }
}