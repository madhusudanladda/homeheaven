package com.homeheaven.controller;

import com.homeheaven.model.Property;
import com.homeheaven.model.User;
import com.homeheaven.repository.PropertyRepository;
import com.homeheaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;

    @GetMapping("/counts")
    public Map<String, Object> counts() {
        Map<String, Object> m = new HashMap<>();
        m.put("users", userRepo.count());
        m.put("properties", propertyRepo.count());
        return m;
    }

    @GetMapping("/properties")
    public List<Property> allProperties() {
        return propertyRepo.findAll();
    }

    @GetMapping("/my-properties")
    public List<Property> myProperties(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return List.of();
        }
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return List.of();
        }
        return propertyRepo.findByOwner(user);
    }

    @DeleteMapping("/property/{id}")
    public Map<String, Object> deleteProperty(@PathVariable Long id, Authentication auth) {
        Map<String, Object> resp = new HashMap<>();
        if (auth == null || !auth.isAuthenticated()) {
            resp.put("success", false);
            resp.put("message", "Not authenticated");
            return resp;
        }

        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            resp.put("success", false);
            resp.put("message", "User not found");
            return resp;
        }

        Property prop = propertyRepo.findById(id).orElse(null);
        if (prop == null) {
            resp.put("success", false);
            resp.put("message", "Property not found");
            return resp;
        }

        // Only allow owner or admin to delete
        if (!prop.getOwner().getId().equals(user.getId())) {
            resp.put("success", false);
            resp.put("message", "You can only delete your own properties");
            return resp;
        }

        propertyRepo.deleteById(id);
        resp.put("success", true);
        resp.put("message", "Property deleted successfully");
        return resp;
    }
}
