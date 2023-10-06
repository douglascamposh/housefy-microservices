package com.dech.housefy.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleCreateDTO {
    private String roleName;
    private List<PermissionDTO> permissions;
}
