package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Role;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passworEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
            logger.error("User with email: " + signupRequest.getEmail() + " already exists on DB");
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
        User user = userRepository.findUserByEmail(signinRequest.getEmail()).orElseThrow(() -> new DataNotFoundException("User not found"));
        String jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();

    }

    @Override
    public JwtAuthResponse refreshToken(JwtAuthResponse jwtAuthResponse) {
        String email = jwtService.getEmailFromToken(jwtAuthResponse.getToken());
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
        jwtService.isTokenValid(jwtAuthResponse.getToken(), user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder().token(jwt).build();
    }

    @Override
    public UserDTO addRoleUser(String email, String role) {
        if (!userRepository.findUserByEmail(email).isPresent()) {
            logger.error("User with email: " + email + " does not exists on DB");
            throw new InternalErrorException("User with email: " + email + " does not exists");
        }
        User user = userRepository.findUserByEmail(email).get();
        List<String> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        User userUpdated = userRepository.save(user);
        return modelMapper.map(userUpdated, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
}
