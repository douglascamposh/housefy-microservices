package com.dech.housefy.repository;

import com.dech.housefy.domain.Salesman;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface ISalesmanRepository extends MongoRepository<Salesman, String>{
    boolean existsByNameAndLastName(String name, String lastName);
}
