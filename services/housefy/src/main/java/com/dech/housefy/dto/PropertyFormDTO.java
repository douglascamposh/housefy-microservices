package com.dech.housefy.dto;

import com.dech.housefy.domain.Address;
import com.dech.housefy.domain.Image;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyFormDTO {

    @NotNull
    private String name;

    @Size(min = 1, max = 1000)
    private String description;

    @NotNull
    private Address address;

    @NotNull
    private String type;

    private String ownerId;

    private List<Image> images = new ArrayList<>();

    private Image imagePlan;
    private List<String> services = new ArrayList<>();

}
