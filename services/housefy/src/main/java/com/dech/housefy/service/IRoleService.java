package com.dech.housefy.service;

import java.util.List;

import com.dech.housefy.dto.RoleDTO;


public interface IRoleService {
    List<RoleDTO> findAll();
    RoleDTO getRole(String rolName);
    RoleDTO save(RoleDTO role);
    RoleDTO update(RoleDTO role);
    void delete(String id);
}
