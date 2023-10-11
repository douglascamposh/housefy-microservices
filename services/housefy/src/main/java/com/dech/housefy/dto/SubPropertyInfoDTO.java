package com.dech.housefy.dto;

import lombok.Data;

@Data
public class SubPropertyInfoDTO {
    private String id;
    private String code;
    private Float size;
    private Float price;
    private String svgId;
    private Float onAccount;
    private Float balance;
    private String propertyId;
    private CustomerDTO customer;
    private Boolean isAvailable;
    private Boolean commonArea;
}
