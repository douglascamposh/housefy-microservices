package com.dech.housefy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private String birthDate;
    private List<UserReferenceDTO> references = new ArrayList<>();
}
