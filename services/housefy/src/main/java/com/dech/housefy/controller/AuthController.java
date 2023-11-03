package com.dech.housefy.controller;

import com.dech.housefy.dto.JwtAuthResponse;
import com.dech.housefy.dto.SigninRequest;
import com.dech.housefy.dto.SignupRequest;
import com.dech.housefy.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(AuthController.BASE_CTRL_URL)
public class AuthController {
    public static final String BASE_CTRL_URL = "api/v1/auth";
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final IUserService userService;

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthResponse singUp(@Valid @RequestBody SignupRequest signupRequest) {
        logger.info("Trying sign in with email: " + signupRequest.getEmail());
        return userService.signup(signupRequest);
    }
    @PostMapping("/logIn")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthResponse login(@Valid @RequestBody SigninRequest signinRequest) {
        logger.info("Trying to log in with email: " + signinRequest.getEmail());
        return userService.signin(signinRequest);
    }

    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthResponse generateToken(@Valid @RequestBody JwtAuthResponse jwtAuth) {
        logger.info("Trying to refresh token: " + jwtAuth.getToken());
        return userService.refreshToken(jwtAuth);
    }

}
