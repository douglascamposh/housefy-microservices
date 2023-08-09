package com.dech.housefy.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;

import java.util.List;

@Setter
@Getter
@Document("property")
public class Property {
    @Id
    private String id;

    @NotNull
    private String name;

    private String description;

    private Address address;

    private String type;

    private String ownerId;

    private List<Image> images;

    private Long totalProperties;

    private Long propertiesAvailable;

}
