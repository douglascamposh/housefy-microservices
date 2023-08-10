package com.dech.housefy.domain;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Setter
@Getter
@Document("property")
public class Property {
    @Id
    private String id;

    @NotNull
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