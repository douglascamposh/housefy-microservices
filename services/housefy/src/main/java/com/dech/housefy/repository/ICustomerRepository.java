package com.dech.housefy.repository;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.dto.CustomerDTO;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

public interface ICustomerRepository extends MongoRepository<Customer, String> {
    Customer findByPhoneNumber(String phoneNumber);
    List<Customer> findByNameContainingOrLastNameContainingOrEmailContainingOrPhoneNumberContaining(String name, String lastName, String email, String phoneNumber);
}
