package com.dech.housefy.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "subproperty")
public class SubProperty {
    @Id
    private String id;
    @Indexed(unique = true)
    private String code;
    private Long size;
    private Float price;
    private Boolean isAvailable;
}
