package com.dech.housefy.repository;

import com.dech.housefy.domain.AdminParam;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IAdminParamRepository extends MongoRepository<AdminParam, String> {
    Optional<AdminParam> findByKey(String key);
}
