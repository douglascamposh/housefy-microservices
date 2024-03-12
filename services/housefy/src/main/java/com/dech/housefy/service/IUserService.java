package com.dech.housefy.service;

import com.dech.housefy.dto.JwtAuthResponse;
import com.dech.housefy.dto.SigninRequest;
import com.dech.housefy.dto.SignupRequest;
import com.dech.housefy.dto.UserDTO;

import java.util.List;

public interface IUserService {
    UserDTO findByEmail(String email);
    JwtAuthResponse signup(SignupRequest signupRequest);
    JwtAuthResponse signin(SigninRequest signinRequest);
    JwtAuthResponse refreshToken(JwtAuthResponse jwtAuthResponse);
    UserDTO addRoleUser(String email, String role);
    List<UserDTO> getUsers();
}
