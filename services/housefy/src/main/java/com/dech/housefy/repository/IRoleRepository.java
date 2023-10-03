package com.dech.housefy.repository;

import com.dech.housefy.domain.RoleDomain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<RoleDomain, String>{
    RoleDomain findRoleByRoleName(String roleName);
}
