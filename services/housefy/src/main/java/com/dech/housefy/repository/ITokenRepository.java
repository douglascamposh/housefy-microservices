package com.dech.housefy.repository;

import com.dech.housefy.domain.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITokenRepository extends MongoRepository<Token, String> {
    Token findTokenByToken(String jwt);
    Token findTokenByUserId(String userId);
}
