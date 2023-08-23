package com.dech.housefy.service;

import com.dech.housefy.dto.UserDTO;

public interface IUserService {
    UserDTO findByEmail(String email);
}
