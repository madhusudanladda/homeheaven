package com.homeheaven.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForgotPasswordDto {
    private String email;
    private String pin;
    private String newPassword;
    private String confirmPassword;
}
