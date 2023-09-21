package com.dech.housefy.dto;

import com.dech.housefy.domain.Address;
import com.dech.housefy.domain.Image;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyInfoDTO {
    private String id;
    @NotNull
    private Address address;
    private List<Image> images = new ArrayList<>();

    private Image imagePlan;
    @NotNull
    @Positive
    private Long totalProperties;

    @NotNull
    @Positive
    private Long propertiesAvailable;

    private List<String> services = new ArrayList<>();

    private List<SubPropertyInfoDTO> subProperties = new ArrayList<>();
}
