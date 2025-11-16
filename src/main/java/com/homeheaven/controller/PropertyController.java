package com.homeheaven.controller;

import com.homeheaven.model.Property;
import com.homeheaven.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired private PropertyService propertyService;

    @GetMapping("/search")
    public List<Property> search(@RequestParam(required = false) String city,
                                 @RequestParam(required = false) Integer minRent,
                                 @RequestParam(required = false) Integer maxRent,
                                 @RequestParam(required = false) String type) {
        return propertyService.search(city, minRent, maxRent, type);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Property> p = propertyService.findById(id);
        if(p.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p.get());
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam Map<String, String> params,
                                    @RequestParam(value = "images", required = false) MultipartFile[] images,
                                    HttpSession session) {
        try {
            Object userIdObj = session.getAttribute("userId");
            if(userIdObj == null) return ResponseEntity.status(401).body("Login required");

            Long userId = (Long) userIdObj;
            // build form map converting rent and sqft to Integer if present
            Map<String, Object> form = new HashMap<>();
            form.put("name", params.get("name"));
            form.put("address", params.get("address"));
            form.put("city", params.get("city"));
            form.put("propertyType", params.get("propertyType"));
            form.put("description", params.get("description"));
            if(params.get("rent") != null) form.put("rent", Integer.parseInt(params.get("rent")));
            if(params.get("sqft") != null && !params.get("sqft").isBlank()) form.put("sqft", Integer.parseInt(params.get("sqft")));
            if(params.get("sharingOption") != null) form.put("sharingOption", params.get("sharingOption"));

            Property saved = propertyService.saveProperty(form, images, userId);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
