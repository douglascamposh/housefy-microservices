package com.dech.housefy.service;

import com.dech.housefy.domain.Property;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.List;

public interface IPropertyService {
    List<Property> findAll();
    Property save(Property property);
}
