package com.dech.housefy.service.impl;

import com.dech.housefy.domain.User;
import com.dech.housefy.dto.UserDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.repository.IUserRepository;
import com.dech.housefy.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            return modelMapper.map(userOptional.get(), UserDTO.class);
        }
        throw new DataNotFoundException("Unable to get User with email: " + email);
    }
}
