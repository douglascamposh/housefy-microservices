package com.dech.housefy.dto;

import java.util.List;

import lombok.Data;

@Data
public class PermissionDTO {
    private String page;
    private List<String> methods;
}
