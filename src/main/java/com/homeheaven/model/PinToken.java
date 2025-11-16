package com.homeheaven.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pin_token")
@Getter @Setter @NoArgsConstructor
public class PinToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String pin; // 4-digit PIN
    private LocalDateTime expiresAt;
}
