package com.dech.housefy.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sale")
public class Sale {
    @Id
    private String id;
    @PositiveOrZero
    private Float onAccount;
    @PositiveOrZero
    private Float balance;
    @PositiveOrZero
    private Float total;
    @NotBlank
    private String subPropertyId;
    @NotBlank
    private String propertyId;
    @NotBlank
    private String customerId;
    private String status;
    private Long createdAt;
    private Long reservationExpiresDate;
    private boolean deleted;
}
