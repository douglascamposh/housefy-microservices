package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Property;
import com.dech.housefy.repository.IPropertyRepository;
import com.dech.housefy.service.IPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;

    @Override
    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    @Override
    public Property save(Property property) {
        Property newProperty = propertyRepository.save(property);
        return newProperty;
    }
}
