package com.dech.housefy.repository;

import com.dech.housefy.domain.Property;
import com.dech.housefy.domain.SubProperty;
import com.dech.housefy.dto.PropertyDTO;

public interface IPropertyRepositoryImpl {
    Property addSubProperty(String propertyId, SubProperty subProperty);
    Property findSubPropertyByCode(String code);
}