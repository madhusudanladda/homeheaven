package com.homeheaven.controller;

import com.homeheaven.dto.*;
import com.homeheaven.model.User;
import com.homeheaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        try {
            if(!dto.getPassword().equals(dto.getConfirmPassword())) return ResponseEntity.badRequest().body("Passwords do not match");
            if(dto.getPin() == null || dto.getPin().length() != 4 || !dto.getPin().matches("\\d{4}")) {
                return ResponseEntity.badRequest().body("PIN must be exactly 4 digits");
            }
            User u = userService.register(dto.getUsername(), dto.getEmail(), dto.getPhone(), dto.getPassword(), dto.getPin());
            return ResponseEntity.ok("Registered");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthDto dto, HttpSession session) {
    User u = userService.authenticate(dto.getUsername(), dto.getPassword());
    if (u == null) return ResponseEntity.status(401).body("Invalid credentials");

    // Create Authentication token with ROLE_ prefix
    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole()));
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(u.getUsername(), null, authorities);

    // Set SecurityContext so Spring Security recognizes the logged-in user
    SecurityContextHolder.getContext().setAuthentication(auth);

    // Store session attributes and SecurityContext in session
    session.setAttribute("userId", u.getId());
    session.setAttribute("username", u.getUsername());
    session.setAttribute("role", u.getRole());
    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

    return ResponseEntity.ok("OK");
}

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto dto) {
        try {
            // Validate passwords match
            if (dto.getNewPassword() == null || !dto.getNewPassword().equals(dto.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            
            // Validate password length
            if (dto.getNewPassword().length() < 6) {
                return ResponseEntity.badRequest().body("Password must be at least 6 characters");
            }

            // Call service to reset password
            boolean ok = userService.resetPasswordByEmailAndPin(dto.getEmail(), dto.getPin(), dto.getNewPassword());
            if (ok) {
                return ResponseEntity.ok("Password reset successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid email or PIN");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
