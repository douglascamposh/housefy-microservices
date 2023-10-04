package com.dech.housefy.repository;

import com.dech.housefy.domain.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<Role, String>{
    Role findRoleByRoleName(String roleName);
}
