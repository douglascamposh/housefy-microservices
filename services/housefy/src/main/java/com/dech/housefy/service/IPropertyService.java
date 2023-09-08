package com.dech.housefy.service;

import com.dech.housefy.domain.Property;
import com.dech.housefy.dto.PropertyDTO;
import com.dech.housefy.dto.PropertyFormDTO;
import com.dech.housefy.dto.SubPropertyDTO;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.List;

public interface IPropertyService {
    List<PropertyDTO> findAll();
    PropertyDTO save(PropertyFormDTO property);
    PropertyDTO findById(String id);
    PropertyDTO update(PropertyDTO property);
    PropertyDTO addSubProperty(String propertyId, SubPropertyDTO subProperty);
    PropertyDTO findByPropertyIdAndSubPropertyId(String propertyId, String subProperty);
}
