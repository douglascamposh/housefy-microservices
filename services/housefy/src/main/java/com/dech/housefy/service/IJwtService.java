package com.dech.housefy.service;

import com.dech.housefy.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtService {
    String getEmailFromToken(String token);

    String generateToken(User user);
    String generateToken(Map<String, Object> claims, User user);

    boolean isTokenValid(String token, User user);
    boolean isTokenValid(String token, UserDetails userDetails);
}
