package com.dech.housefy.service.facade;

import com.dech.housefy.dto.PropertyDTO;
import com.dech.housefy.dto.PropertyInfoDTO;
import com.dech.housefy.dto.SoldPropertyFormDTO;
import com.dech.housefy.dto.SubPropertyDTO;
import com.dech.housefy.dto.SubPropertyInfoDTO;

import java.util.List;

public interface IPropertyFacade {
    List<SubPropertyInfoDTO> getPropertiesInfo(String propertyId);
    SoldPropertyFormDTO soldProperty(SoldPropertyFormDTO soldPropertyFormDTO);
    SubPropertyDTO updateSubProperty(String propertyId, SubPropertyDTO subPropertyDTO);
    PropertyDTO updateProperty(PropertyDTO propertyDTO);
    void deleteSubProperty(String propertyId, String subPropertyId);
}
