package com.dech.housefy.service;

import com.dech.housefy.domain.Property;
import com.dech.housefy.dto.PropertyDTO;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.List;

public interface IPropertyService {
    List<PropertyDTO> findAll();
    PropertyDTO save(PropertyDTO property);
    PropertyDTO findById(String id);
}
