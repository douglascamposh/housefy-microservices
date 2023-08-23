package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest extends UserDTO {
    @NotBlank
    private String password;
}
