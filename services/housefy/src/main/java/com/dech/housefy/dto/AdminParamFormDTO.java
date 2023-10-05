package com.dech.housefy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminParamFormDTO {
    private String id;
    @NotBlank
    private String paramKey;
    @NotBlank
    private String paramValue;
}
