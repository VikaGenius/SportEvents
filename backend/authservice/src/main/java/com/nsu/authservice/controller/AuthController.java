package com.nsu.authservice.controller;

import com.nsu.authservice.model.AuthenticationResponse;
import com.nsu.authservice.model.User;
import com.nsu.authservice.repository.UserRepository;
import com.nsu.authservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User login) {
        User user = userRepository.findByEmail(login.getEmail());
        if (user != null && passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            final String jwt = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

}
