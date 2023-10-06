package com.dech.housefy.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleDTO {
    private String id;
    private String roleName;
    private List<PermissionDTO> permissions;
}
