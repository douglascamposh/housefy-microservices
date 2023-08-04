package com.dech.housefy.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("image")
public class Image {
    @Id
    private String id;
    private String url;
    private Boolean isCover;
    private Integer weight;
}
