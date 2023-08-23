package com.dech.housefy.service;

import com.dech.housefy.domain.User;

public interface IJwtService {
    String extractEmail(String token);

    String generateToken(User user);

    boolean isTokenValid(String token, User userDetails);
}
