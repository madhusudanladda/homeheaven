package com.homeheaven.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForgotDto {
    private String code; // PIN or new password code
    private String newPassword;
}
