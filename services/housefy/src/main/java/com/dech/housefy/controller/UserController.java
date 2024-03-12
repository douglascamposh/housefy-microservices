package com.dech.housefy.controller;

import com.dech.housefy.domain.User;
import com.dech.housefy.dto.UserDTO;
import com.dech.housefy.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(UserController.BASE_CTRL_URL)
public class UserController {
    public static final String BASE_CTRL_URL = "api/v1/users";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final IUserService userService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        logger.info("Getting all users");
        return userService.getUsers();
    }
}
