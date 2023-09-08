package com.dech.housefy.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "property")
public class Property {
    @Id
    private String id;

    @NotBlank
    private String name;

    private String description;

    private Address address;

    @NotBlank
    private String type;

    private String ownerId;

    private List<Image> images = new ArrayList<>();

    private Image imagePlan;

    @Builder.Default
    private List<SubProperty> subProperties = Collections.emptyList();

}
