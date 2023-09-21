package com.dech.housefy.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("address")
public class Address extends BaseAddress {
    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;
}
