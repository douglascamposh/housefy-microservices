package com.dech.housefy.domain;

import java.util.List;

import lombok.Data;

@Data
public class Permission {
    private String page;
    private List<String> methods;
}
