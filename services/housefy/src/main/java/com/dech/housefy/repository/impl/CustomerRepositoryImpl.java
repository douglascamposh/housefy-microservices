package com.dech.housefy.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.repository.ICustomerRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements ICustomerRepositoryImpl{
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Customer> searchCustomers(HashMap<String, String> fields) {
        List<Criteria> criteriaList = fields.entrySet().stream()
                .map(entry -> Criteria.where(entry.getKey()).regex(Pattern.quote((String) entry.getValue()), "i"))
                .collect(Collectors.toList());

        Criteria criteria = new Criteria().orOperator(criteriaList.toArray(new Criteria[0]));

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Customer.class);
    }
}
