package com.dech.housefy.repository;

import com.dech.housefy.domain.Property;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPropertyRepository extends MongoRepository<Property, String> {

}
