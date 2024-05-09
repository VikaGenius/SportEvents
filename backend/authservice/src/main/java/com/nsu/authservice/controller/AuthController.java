package com.nsu.authservice.controller;

import com.nsu.authservice.model.AuthenticationResponse;
import com.nsu.authservice.model.User;
import com.nsu.authservice.repository.UserRepository;
import com.nsu.authservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        System.out.println("AAAAAAAAAAAAAAAA");
        System.out.println("BBBBBBBBBBB");
        System.out.println("AAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAA");
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        System.out.println(user.getFirstName() + " " + user.getEmail() + " " + user.getLastName() + " " + user.getPhoneNumber() + " ");
        System.out.println("AAAAAAAAAAAAAAAAAa");
        userRepository.save(user);
        System.out.println("User saved");
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User login) {
        System.out.println("AAAAAAAAAAAAAAAA");
        System.out.println("CCCCCCCCCCCCCCCCC");
        System.out.println("AAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAA");

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
