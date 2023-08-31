package com.dech.housefy.dto;

import com.dech.housefy.domain.Address;
import com.dech.housefy.domain.Image;

import com.dech.housefy.domain.SubProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
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
    private String name;

    @Size(min = 1, max = 1000)
    private String description;

    @NotNull
    private Address address;

    @NotNull
    private String type;

    private String ownerId;

    private List<Image> images = new ArrayList<>();

    @NotNull
    @Positive
    private Long totalProperties;

    @NotNull
    @Positive
    private Long propertiesAvailable;

    private List<SubPropertyDTO> subProperties = new ArrayList<>();
}
