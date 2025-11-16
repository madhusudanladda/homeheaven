package com.homeheaven.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterDto {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String pin;
}
