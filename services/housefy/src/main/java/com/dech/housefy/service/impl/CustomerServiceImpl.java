package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.dto.SearchRequestDTO;
import com.dech.housefy.dto.UserReferenceDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.error.InternalErrorException;
import com.dech.housefy.repository.ICustomerRepository;
import com.dech.housefy.repository.ICustomerRepositoryImpl;
import com.dech.housefy.service.ICustomerService;
import com.dech.housefy.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final ICustomerRepositoryImpl customerRepositoryImpl;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(null);
        Customer customer = customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber());
        if (customer == null) {
            List<UserReferenceDTO> references = customerDTO.getReferences();
            if (references.size() == 0) {
                throw new InternalErrorException("The customer must have at least one reference");
            }
            references.forEach(reference -> reference.setId(new ObjectId().toString()));
            customerDTO.setReferences(references);
            Customer customerToSave = modelMapper.map(customerDTO, Customer.class);
            //Todo remove the birthDate for customer
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
    @Override
    public List<CustomerDTO> searchCustomers(SearchRequestDTO criteria) {
        HashMap<String, String> fields = Utils.getFields(criteria);
        List<Customer> customers = fields.isEmpty()? customerRepository.findAll() : customerRepositoryImpl.searchCustomers(fields);
        return customers.stream()
            .map(customer -> modelMapper.map(customer, CustomerDTO.class))
            .collect(Collectors.toList());
    }
}
