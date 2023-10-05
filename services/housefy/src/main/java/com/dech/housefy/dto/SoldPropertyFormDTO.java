package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class SoldPropertyFormDTO {
    @PositiveOrZero
    private Float onAccount;
    private Float total;
    @NotBlank
    private String subPropertyId;
    @NotBlank
    private String propertyId;
    private CustomerDTO customer;
    @NotBlank
    private String status;
}
