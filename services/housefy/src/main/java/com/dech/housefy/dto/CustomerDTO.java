package com.dech.housefy.dto;

import com.dech.housefy.domain.BaseAddress;
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
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String ci;
    private String extensionCi;
    private BaseAddress address;
    private List<UserReferenceDTO> references = new ArrayList<>();
}
