package com.nsu.sport_events.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nsu.sport_events.auth_service.model.AuthenticationResponse;
import com.nsu.sport_events.auth_service.model.User;
import com.nsu.sport_events.auth_service.repository.UserRepository;
import com.nsu.sport_events.auth_service.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User login) {
        var optionalUser = userRepository.findByEmail(login.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                final String jwt = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
            }
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

}
