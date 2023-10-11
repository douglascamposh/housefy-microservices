package com.dech.housefy.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dech.housefy.domain.Role;
import com.dech.housefy.dto.RoleCreateDTO;
import com.dech.housefy.dto.RoleDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
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
        Optional<Role> role= roleRepository.findRoleByRoleName(rolName);
        if(role.isPresent()){
            return modelMapper.map(role, RoleDTO.class);
        }
        throw new DataNotFoundException("Unable to get Role by Name: " + rolName);
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO save(RoleCreateDTO roleCreateDTO) {
        Optional<Role> existingRole = roleRepository.findRoleByRoleName(roleCreateDTO.getRoleName());
        if(!existingRole.isPresent()){
            Role role = modelMapper.map(roleCreateDTO, Role.class);
            Role newRole = roleRepository.save(role);
            return modelMapper.map(newRole, RoleDTO.class);
        }
        throw new DuplicateDataException("A role with this name already exists." + roleCreateDTO.getRoleName());
    }

    @Override
    public RoleDTO update(RoleDTO roleDTO) {
        Optional<Role> existingIdRole = roleRepository.findById(roleDTO.getId());
        if(!existingIdRole.isPresent()){
            throw new DataNotFoundException("Unable to get Role by id: " + roleDTO.getId());
        }

        Optional<Role> existingRole = roleRepository.findRoleByRoleName(roleDTO.getRoleName());
        if(existingRole.isPresent()){
            throw new DuplicateDataException("A role with this name already exists. : " + roleDTO.getRoleName());
        }
        Role roleToUpdate = modelMapper.map(roleDTO, Role.class);
        return modelMapper.map(roleRepository.save(roleToUpdate), RoleDTO.class);
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

}
