package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class SoldPropertyFormDTO {
    @PositiveOrZero
    private Float onAccount;
    @PositiveOrZero
    private Float balance;
//    @PositiveOrZero
//    private Float total; Todo: the todal should be returned from the subproperty, also create soldpropertyDtoResponse to add the code apartment
    @NotBlank
    private String subPropertyId;
    @NotBlank
    private String propertyId;
    private CustomerDTO customer;
}
