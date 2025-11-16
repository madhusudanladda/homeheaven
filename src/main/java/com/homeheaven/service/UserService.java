package com.homeheaven.service;


import com.homeheaven.model.User;
import com.homeheaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Service
@Transactional
public class UserService {
    @Autowired private UserRepository userRepo;
    

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(String username, String email, String phone, String password, String pin) throws Exception {
        if(userRepo.findByUsername(username).isPresent()) throw new Exception("Username exists");
        if(userRepo.findByEmail(email).isPresent()) throw new Exception("Email exists");

        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPhone(phone);
        u.setPasswordHash(encoder.encode(password));
        u.setRole("USER");
        u.setPin(pin); // Use PIN provided by user during registration
        
        return userRepo.save(u);
    }

    public User authenticate(String username, String password) {
        Optional<User> o = userRepo.findByUsername(username);
        if(o.isEmpty()) return null;
        User u = o.get();
        if(encoder.matches(password, u.getPasswordHash())) return u;
        return null;
    }

    public boolean verifyPinAndReset(String pin, String newPassword) {
        // Find user by PIN (no email needed)
        Optional<User> u = userRepo.findByPin(pin);
        if(u.isEmpty()) return false;
        
        User user = u.get();
        
        // PIN is correct, reset password
        user.setPasswordHash(encoder.encode(newPassword));
        userRepo.save(user);
        
        return true;
    }

    public boolean verifyPinAndReset(String email, String pin, String newPassword) {
        // Legacy method - kept for compatibility
        // Verify the PIN against user's stored PIN
        Optional<User> u = userRepo.findByEmail(email);
        if(u.isEmpty()) return false;
        
        User user = u.get();
        
        // Check if provided PIN matches user's PIN
        if(!user.getPin().equals(pin)) {
            return false;
        }
        
        // PIN is correct, reset password
        user.setPasswordHash(encoder.encode(newPassword));
        userRepo.save(user);
        
        return true;
    }

    public boolean resetPasswordByEmailAndPin(String email, String pin, String newPassword) {
        // Find user by email
        Optional<User> u = userRepo.findByEmail(email);
        if (u.isEmpty()) {
            return false; // User not found
        }

        User user = u.get();

        // Validate PIN matches user's stored PIN
        if (pin == null || !user.getPin().equals(pin)) {
            return false; // PIN mismatch
        }

        // Hash new password with BCrypt and save
        user.setPasswordHash(encoder.encode(newPassword));
        userRepo.save(user);

        return true;
    }
}
