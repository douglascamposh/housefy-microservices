package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
