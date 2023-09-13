package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Property;
import com.dech.housefy.domain.SubProperty;
import com.dech.housefy.dto.PropertyDTO;
import com.dech.housefy.dto.PropertyFormDTO;
import com.dech.housefy.dto.SubPropertyDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.IPropertyRepository;
import com.dech.housefy.repository.IPropertyRepositoryImpl;
import com.dech.housefy.service.IPropertyService;
import com.dech.housefy.service.facade.impl.PropertyFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    private final IPropertyRepositoryImpl propertyRepositoryImpl;
    private final Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

    @Override
    public List<PropertyDTO> findAll() {
        return propertyRepository.findAll().stream().map(property -> modelMapper.map(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PropertyDTO save(PropertyFormDTO propertyDTO) {
        Property property = modelMapper.map(propertyDTO, Property.class);
        Property newProperty = propertyRepository.save(property);
        logger.info("New Property created: " + newProperty.getId());
        return modelMapper.map(newProperty, PropertyDTO.class);
    }

    @Override
    public PropertyDTO findById(String id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            return modelMapper.map(property.get(), PropertyDTO.class);
        }
        logger.error("Unable to get Property by id: " + id);
        throw new DataNotFoundException("Unable to get Property by id: " + id);
    }

    @Override
    public PropertyDTO update(PropertyDTO property) {
        findById(property.getId());
        Property propertyToUpdate = modelMapper.map(property, Property.class);
        return modelMapper.map(propertyRepository.save(propertyToUpdate), PropertyDTO.class);
    }

    @Override
    public PropertyDTO addSubProperty(String propertyId, SubPropertyDTO subPropertyDTO) {
        PropertyDTO propertyDTO = this.findById(propertyId);
        Optional<SubPropertyDTO> subPropertyFound = propertyDTO.getSubProperties().stream()
                .filter(prop -> prop.getCode() != null && prop.getCode().equals(subPropertyDTO.getCode())).findFirst();
        if (subPropertyFound.isPresent()) {
            throw new DuplicateDataException("The element with code: " + subPropertyDTO.getCode() + " is already added");
        }
        SubProperty subProperty = modelMapper.map(subPropertyDTO, SubProperty.class);
        subProperty.setId(new ObjectId().toString());
        return modelMapper.map(propertyRepositoryImpl.addSubProperty(propertyId, subProperty), PropertyDTO.class);
    }

    @Override
    public PropertyDTO updateSubProperty(String propertyId, SubPropertyDTO subProperty) {
        PropertyDTO propertyDTO = this.findByPropertyIdAndSubPropertyId(propertyId, subProperty.getId());
        SubPropertyDTO subPropertyFound = propertyDTO.getSubProperties().stream().filter(prop -> prop.getId().equals(subProperty.getId())).findFirst().get();
        if (!subPropertyFound.getCode().equals(subProperty.getCode())) {
            List<SubPropertyDTO> subProperties = propertyDTO.getSubProperties().stream().filter(prop -> prop.getCode().equals(subProperty.getCode())).collect(Collectors.toList());
            if (subProperties.size() > 0) {
                throw new DuplicateDataException("The element with code: " + subProperty.getCode() + " is already added");
            }
        }
        SubProperty subPropertyToUpdate = modelMapper.map(subProperty, SubProperty.class);
        return modelMapper.map(propertyRepositoryImpl.updateSubProperty(propertyId, subPropertyToUpdate), PropertyDTO.class);
    }

    @Override
    public PropertyDTO deleteSubProperty(String propertyId, String subProperty) {
        propertyRepositoryImpl.deleteSubProperty(propertyId, subProperty);
        return this.findById(propertyId);
    }

    @Override
    public PropertyDTO findByPropertyIdAndSubPropertyId(String propertyId, String subPropertyId) {
        Property property = propertyRepositoryImpl.findByPropertyIdAndSubPropertyId(propertyId, subPropertyId);
        if (property == null) {
            throw new DataNotFoundException("Unable to ge Property by subPropertyId: " + subPropertyId);
        }
        return modelMapper.map(property, PropertyDTO.class);
    }
}
