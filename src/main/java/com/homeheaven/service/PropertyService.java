package com.homeheaven.service;

import com.homeheaven.model.Property;
import com.homeheaven.model.User;
import com.homeheaven.repository.PropertyRepository;
import com.homeheaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired private PropertyRepository propertyRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private FileStorageService fileStorageService;

    public Property saveProperty(Map<String, Object> form, MultipartFile[] images, Long ownerId) throws Exception {
        // form contains keys: name,address,city,propertyType,rent,description,sqft,sharingOption
        Optional<User> ou = userRepo.findById(ownerId);
        if(ou.isEmpty()) throw new Exception("Owner not found");
        User owner = ou.get();

        String desc = (String) form.get("description");
        if(wordCount(desc) < 100) throw new Exception("Description must be at least 100 words");

        String type = (String) form.get("propertyType");
        if("Flat".equalsIgnoreCase(type) && (form.get("sqft") == null)) throw new Exception("Sqft required for Flat");
        if("PG".equalsIgnoreCase(type) && (form.get("sharingOption") == null || ((String)form.get("sharingOption")).isBlank()))
            throw new Exception("Sharing option required for PG");

        Property p = new Property();
        p.setName((String) form.get("name"));
        p.setAddress((String) form.get("address"));
        p.setCity((String) form.get("city"));
        p.setPropertyType(type);
        p.setRent((Integer) form.get("rent"));
        p.setDescription(desc);
        if(form.get("sqft") != null) p.setSqft((Integer) form.get("sqft"));
        if(form.get("sharingOption") != null) p.setSharingOption((String) form.get("sharingOption"));
        p.setOwner(owner);

        // save first to get ID (optional)
        Property saved = propertyRepo.save(p);

        // save images and set filenames
        if(images != null && images.length > 0) {
            List<String> filenames = new ArrayList<>();
            for(MultipartFile f: images) {
                if(f == null || f.isEmpty()) continue;
                String fn;
                try {
                    fn = fileStorageService.save(f);
                    filenames.add(fn);
                } catch (Exception ex) {
                    // continue saving others
                }
            }
            if(!filenames.isEmpty()) {
                saved.setImages(String.join(",", filenames));
                propertyRepo.save(saved);
            }
        }

        return saved;
    }

    private int wordCount(String s) {
        if(s == null) return 0;
        String[] tokens = s.trim().split("\s+");
        return (tokens.length == 1 && tokens[0].isEmpty()) ? 0 : tokens.length;
    }

    public List<Property> search(String city, Integer minRent, Integer maxRent, String type) {
        return propertyRepo.search(
                (city == null || city.isBlank()) ? null : city,
                (type == null || type.isBlank()) ? null : type,
                minRent, maxRent
        );
    }

    public Optional<Property> findById(Long id) {
        return propertyRepo.findById(id);
    }

    public List<Property> findAll() {
        return propertyRepo.findAll();
    }
}
