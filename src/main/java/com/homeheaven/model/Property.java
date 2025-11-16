package com.homeheaven.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "property")
@Getter @Setter @NoArgsConstructor
public class Property {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String address;

    private String city;

    private String propertyType; // PG / Hostel / Flat / House

    private Integer rent;

    @Column(length = 5000)
    private String description;

    // filenames separated by comma
    private String images;

    // for flats
    private Integer sqft;

    // for PG
    private String sharingOption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
}
