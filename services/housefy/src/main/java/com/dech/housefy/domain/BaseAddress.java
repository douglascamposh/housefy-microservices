package com.dech.housefy.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAddress {
    @NotNull
    @Size(min = 1, max = 100)
    private String street;
    private String reference;
    @NotNull
    @Size(min = 1, max = 10)
    private String streetNumber;
    @NotNull
    @Size(min = 1, max = 25)
    private String city;
    @NotNull
    @Size(min = 1, max = 25)
    private String country;
    @NotNull
    @Size(min = 1, max = 10)
    private String state;
}
