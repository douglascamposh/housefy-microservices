package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Property;
import com.dech.housefy.dto.PropertyDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.repository.IPropertyRepository;
import com.dech.housefy.service.IPropertyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PropertyDTO> findAll() {
        return propertyRepository.findAll().stream().map(property -> modelMapper.map(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PropertyDTO save(PropertyDTO propertyDTO) {
        Property property = modelMapper.map(propertyDTO, Property.class);
        Property newProperty = propertyRepository.save(property);
        return modelMapper.map(newProperty, PropertyDTO.class);
    }

    @Override
    public PropertyDTO findById(String id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            return modelMapper.map(property.get(), PropertyDTO.class);
        }
        throw new DataNotFoundException("Unable to get Property by id: " + id);
    }
}
