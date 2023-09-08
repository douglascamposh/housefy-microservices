package com.dech.housefy.service;

import com.dech.housefy.dto.CustomerDTO;

public interface ICustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO findById(String customerId);
    CustomerDTO findByNumberPhone(String phone);
}