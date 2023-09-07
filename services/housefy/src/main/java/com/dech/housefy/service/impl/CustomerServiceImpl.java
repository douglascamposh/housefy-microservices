package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.ICustomerRepository;
import com.dech.housefy.service.ICustomerService;
import com.dech.housefy.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(null);
        Customer customer = customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber());
        if (customer == null) {
            Customer customerToSave = modelMapper.map(customerDTO, Customer.class);
            customerToSave.setBirthDate(Utils.convertStringToDate(customerDTO.getBirthDate()));
            return modelMapper.map(customerRepository.save(customerToSave), CustomerDTO.class);
        }
        throw new DuplicateDataException("There is a customer created with the same number phone: " + customerDTO.getPhoneNumber());
    }

    @Override
    public CustomerDTO findById(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return modelMapper.map(customer.get(), CustomerDTO.class);
        }
        throw new DataNotFoundException("Unable to get Customer with Id: " + customerId);
    }

    @Override
    public CustomerDTO findByNumberPhone(String phone) {
        Customer customer = customerRepository.findByPhoneNumber(phone);
        if (customer != null) {
            return modelMapper.map(customer, CustomerDTO.class);
        }
        throw new DataNotFoundException("Unable to get Customer with Number phone: " + phone);
    }
}
