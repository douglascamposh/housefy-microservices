package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
