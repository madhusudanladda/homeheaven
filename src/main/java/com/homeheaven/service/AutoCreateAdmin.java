package com.homeheaven.service;

import com.homeheaven.model.User;
import com.homeheaven.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AutoCreateAdmin implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (userRepository.findByEmail("admin@homeheaven.com").isPresent()) {
            log.info("Admin user already exists");
            return;
        }

        // Create new admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@homeheaven.com");
        admin.setPhone("9999999999");
        admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
        admin.setRole("ADMIN");
        admin.setPin("1234");

        userRepository.save(admin);
        log.info("Admin user created successfully - email: admin@homeheaven.com, password: Admin@123");
    }
}
