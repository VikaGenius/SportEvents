package com.nsu.sport_events.auth_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;

//import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nsu.sport_events.auth_service.DTO.AuthenticationResponse;
import com.nsu.sport_events.auth_service.DTO.ResetPasswordRequest;
import com.nsu.sport_events.auth_service.model.User;
import com.nsu.sport_events.auth_service.repository.UserRepository;
import com.nsu.sport_events.auth_service.util.JwtUtil;
import com.nsu.sport_events.smtp_service.service.EmailService;

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

    @Autowired
    private EmailService emailService;

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

    @PostMapping("/forgot")
    public ResponseEntity<?> processForgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No user registered with this email");
        }

        User user = userOptional.get();
        String token = jwtUtil.generateToken(user.getEmail());
        user.setResetToken(token);
        userRepository.save(user);

        emailService.sendSimpleMessage(
            email,
            "Reset Password",
            "To reset your password, click here: " + "http://localhost:3000/reset/" + token
        );

        return ResponseEntity.ok("Reset password link sent to your email.");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password has been reset successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
