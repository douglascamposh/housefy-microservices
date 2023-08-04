package com.dech.housefy.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("address")
public class Address {
    private String street;
    private String reference;
    private String streetNumber;
    private String city;
    private String country;
    private String state;
    private Float latitude;
    private Float longitude;
}
