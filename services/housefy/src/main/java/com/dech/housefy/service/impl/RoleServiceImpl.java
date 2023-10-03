package com.dech.housefy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dech.housefy.domain.RoleDomain;
import com.dech.housefy.dto.RoleDTO;
import com.dech.housefy.repository.IRoleRepository;
import com.dech.housefy.service.IRoleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoleDTO getRole(String rolName) {
        RoleDomain role= roleRepository.findRoleByRoleName(rolName);
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        RoleDomain property = modelMapper.map(roleDTO, RoleDomain.class);
        RoleDomain newRole = roleRepository.save(property);
        return modelMapper.map(newRole, RoleDTO.class);
    }

    @Override
    public RoleDTO update(RoleDTO role) {
        roleRepository.findById(role.getId());
        RoleDomain roleToUpdate = modelMapper.map(role, RoleDomain.class);
        return modelMapper.map(roleRepository.save(roleToUpdate), RoleDTO.class);
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

}
