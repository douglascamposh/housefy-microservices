package com.dech.housefy.repository;

import com.dech.housefy.domain.Role;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<Role, String>{
    Optional<Role> findRoleByRoleName(String roleName);
}
