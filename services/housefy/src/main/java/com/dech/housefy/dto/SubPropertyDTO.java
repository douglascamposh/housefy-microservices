package com.dech.housefy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubPropertyDTO {
    private String id;
    @NotBlank
    private String code;
    @Positive
    private Float size;
    private Float price;
    private String svgId;
    private Boolean commonArea = false;
}
