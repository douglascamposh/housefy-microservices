package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserReferenceDTO {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String relationship;
}
