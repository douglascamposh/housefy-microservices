package com.dech.housefy.service.facade.impl;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.domain.Sale;
import com.dech.housefy.dto.*;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.service.ICustomerService;
import com.dech.housefy.service.IPropertyService;
import com.dech.housefy.service.ISaleService;
import com.dech.housefy.service.facade.IPropertyFacade;
import com.dech.housefy.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PropertyFacadeImpl implements IPropertyFacade {
    private final IPropertyService propertyService;
    private final ISaleService saleService;
    private final ICustomerService customerService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(PropertyFacadeImpl.class);

    @Override
    public PropertyInfoDTO getPropertiesInfo(String propertyId) {
        PropertyInfoDTO propertyInfo = modelMapper.map(propertyService.findById(propertyId), PropertyInfoDTO.class);
        List<SaleDTO> saleDTOList = saleService.findAllByPropertyId(propertyId);
        List<SubPropertyInfoDTO> propertyInfoDTOS = propertyInfo.getSubProperties().stream()
                .map(subPropertyInfoDTO -> {
                    Optional<SaleDTO> saleOptional = saleDTOList.stream().filter(saleDTO -> saleDTO.getSubPropertyId().equals(subPropertyInfoDTO.getId())).findFirst();
                    if (saleOptional.isPresent()) {
                        subPropertyInfoDTO.setBalance(saleOptional.get().getBalance());
                        subPropertyInfoDTO.setOnAccount(saleOptional.get().getOnAccount());
                        subPropertyInfoDTO.setPropertyId(propertyId);
                    }
                    return subPropertyInfoDTO;
                }
        ).collect(Collectors.toList());
        propertyInfo.setSubProperties(propertyInfoDTOS);
        propertyInfo.setTotalProperties(propertyInfoDTOS.stream().count());
        propertyInfo.setPropertiesAvailable(propertyInfoDTOS.stream().filter(subProp -> subProp.getBalance() == null).count());
        return propertyInfo;
    }

    @Override
    public SoldPropertyFormDTO soldProperty(SoldPropertyFormDTO soldForm) {
        PropertyDTO propertyDTO = propertyService.findByPropertyIdAndSubPropertyId(soldForm.getPropertyId(), soldForm.getSubPropertyId());
        CustomerDTO customerDTO;
        if (soldForm.getCustomer().getId() == null) {
            customerDTO = customerService.createCustomer(soldForm.getCustomer());
            logger.info("Customer created with Id: " + customerDTO.getId());
        } else {
            customerDTO = customerService.findById(soldForm.getCustomer().getId());
        }
        soldForm.setCustomer(customerDTO);
        //Todo it should get only the sub property that we need
        soldForm.setTotal(getSubPropertyById(soldForm.getSubPropertyId(), propertyDTO.getSubProperties()).getPrice());
        SoldPropertyFormDTO soldPropertyDTO = saleService.create(soldForm);
        soldPropertyDTO.setCustomer(customerDTO);
        return soldPropertyDTO;
    }

    private SubPropertyDTO getSubPropertyById(String subPropertyId, List<SubPropertyDTO> subPropertyDTOList) {
        return subPropertyDTOList.stream()
                .filter(subPropertyDTO -> subPropertyDTO.getId().equals(subPropertyId))
                .findFirst()
                .orElseThrow(()-> new DataNotFoundException("Unable to get SubProperty with Id: " + subPropertyId));
    }
}
