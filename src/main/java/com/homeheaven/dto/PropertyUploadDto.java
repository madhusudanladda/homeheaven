package com.homeheaven.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class PropertyUploadDto {
    private String name;
    private String address;
    private String city;
    private String propertyType;
    private Integer rent;
    private String description;
    private Integer sqft; // for flats
    private String sharingOption; // for PGs
    private MultipartFile[] images;
}
