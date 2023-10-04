package com.dech.housefy.service;

import java.util.List;

import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.dto.SearchRequestDTO;

public interface ICustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO findById(String customerId);
    CustomerDTO findByNumberPhone(String phone);
    List<CustomerDTO> searchCustomers(String criteria);
}
