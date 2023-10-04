package com.dech.housefy.service;

import java.util.List;

import com.dech.housefy.dto.RoleCreateDTO;
import com.dech.housefy.dto.RoleDTO;


public interface IRoleService {
    List<RoleDTO> findAll();
    RoleDTO getRole(String rolName);
    RoleDTO save(RoleCreateDTO roleCreateDTO);
    RoleDTO update(RoleDTO roleDTO);
    void delete(String id);
}
