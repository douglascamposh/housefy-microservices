package com.dech.housefy.repository;

import com.dech.housefy.domain.Customer;

import java.util.HashMap;
import java.util.List;

public interface ICustomerRepositoryImpl {
    public List<Customer> searchCustomers(HashMap<String, String> fields);
}
