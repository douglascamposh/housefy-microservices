package com.dech.housefy.dto;

import com.dech.housefy.domain.Address;
import com.dech.housefy.domain.Image;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyDTO {

    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private String description;

    private Address address;

    private String type;

    private String ownerId;

    private List<Image> images;

    private Long totalProperties;

    private Long propertiesAvailable;

}
