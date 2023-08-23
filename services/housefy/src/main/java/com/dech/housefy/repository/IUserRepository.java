package com.dech.housefy.repository;

import com.dech.housefy.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByEmail(String email);
}
