package com.dech.housefy.controller;

import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.service.ICustomerService;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(CustomerController.BASE_CTRL_URL)
public class CustomerController {
    public static final String BASE_CTRL_URL = "api/v1/customers";
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    private final ICustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> searchUsers(@RequestParam("criteria") @Size(min = 3)String criteria) {
        logger.info("Show criteria: " + criteria);
        return customerService.searchUsers(criteria);
    }
}
