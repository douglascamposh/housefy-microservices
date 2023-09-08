package com.dech.housefy.service.facade;

import com.dech.housefy.dto.PropertyInfoDTO;
import com.dech.housefy.dto.SoldPropertyFormDTO;

public interface IPropertyFacade {
    PropertyInfoDTO getPropertiesInfo(String propertyId);
    SoldPropertyFormDTO soldProperty(SoldPropertyFormDTO soldPropertyFormDTO);
}
