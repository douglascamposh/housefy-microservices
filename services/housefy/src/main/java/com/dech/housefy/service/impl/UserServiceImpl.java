package com.dech.housefy.service.impl;

import com.dech.housefy.domain.User;
import com.dech.housefy.dto.JwtAuthResponse;
import com.dech.housefy.dto.SigninRequest;
import com.dech.housefy.dto.SignupRequest;
import com.dech.housefy.dto.UserDTO;
import com.dech.housefy.enums.RoleEnums;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.InternalErrorException;
import com.dech.housefy.repository.IUserRepository;
import com.dech.housefy.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passworEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            return modelMapper.map(userOptional.get(), UserDTO.class);
        }
        throw new DataNotFoundException("Unable to get User with email: " + email);
    }

    @Override
    public JwtAuthResponse signup(SignupRequest signupRequest) {
        if (userRepository.findUserByEmail(signupRequest.getEmail()).isPresent()) {
            throw new InternalErrorException("User with email: " + signupRequest.getEmail() + " already exists");
        }
        User user = modelMapper.map(signupRequest, User.class);
        List<String> roles = new ArrayList<String>();
        roles.add(RoleEnums.ROLE_USER.toString());
        user.setRoles(roles);
        user.setPassword(passworEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
        );
        User user = userRepository.findUserByEmail(signinRequest.getEmail()).orElseThrow(() -> new InternalErrorException("User not found"));
        String jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();

    }
}
