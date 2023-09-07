package com.dech.housefy.dto;

import lombok.Data;

@Data
public class SaleDTO {
    private String id;
    private Float onAccount;
    private Float balance;
    private Float total;
    private String subPropertyId;
    private String propertyId;
    private String customerId;
}
