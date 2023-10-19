package com.dech.housefy.service.facade.impl;

import com.dech.housefy.domain.Image;
import com.dech.housefy.dto.*;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.InternalErrorException;
import com.dech.housefy.service.ICustomerService;
import com.dech.housefy.service.IPropertyService;
import com.dech.housefy.service.ISaleService;
import com.dech.housefy.service.facade.IPropertyFacade;
import lombok.RequiredArgsConstructor;
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
public class PropertyFacadeImpl implements IPropertyFacade {
    private final IPropertyService propertyService;
    private final ISaleService saleService;
    private final ICustomerService customerService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(PropertyFacadeImpl.class);

    @Override
    public List<SubPropertyInfoDTO> getPropertiesInfo(String propertyId) {
        PropertyInfoDTO propertyInfo = modelMapper.map(propertyService.findById(propertyId), PropertyInfoDTO.class);
        List<SaleDTO> saleDTOList = saleService.findAllByPropertyId(propertyId);
        return propertyInfo.getSubProperties().stream()
                .map(subPropertyInfoDTO -> {
                    if(subPropertyInfoDTO.getCommonArea()) {
                        subPropertyInfoDTO.setIsAvailable(false);
                        return subPropertyInfoDTO;
                    }
                    Optional<SaleDTO> saleOptional = saleDTOList.stream().filter(saleDTO -> saleDTO.getSubPropertyId().equals(subPropertyInfoDTO.getId())).findFirst();
                    subPropertyInfoDTO.setPropertyId(propertyId);
                    if (saleOptional.isPresent()) {
                        subPropertyInfoDTO.setBalance(saleOptional.get().getBalance());
                        subPropertyInfoDTO.setOnAccount(saleOptional.get().getOnAccount());
                        subPropertyInfoDTO.setIsAvailable(false);
                        subPropertyInfoDTO.setStatus(saleOptional.get().getStatus());
                        subPropertyInfoDTO.setReservationExpiresDate(saleOptional.get().getReservationExpiresDate());
                        CustomerDTO customer = customerService.findById(saleOptional.get().getCustomerId());
                        subPropertyInfoDTO.setCustomer(customer);
                    } else {
                        subPropertyInfoDTO.setIsAvailable(true);
                    }
                    return subPropertyInfoDTO;
                }
        ).collect(Collectors.toList());
//        propertyInfo.setTotalProperties(propertyInfoDTOS.stream().count());
//        propertyInfo.setPropertiesAvailable(propertyInfoDTOS.stream().filter(subProp -> subProp.getBalance() == null).count());
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

    @Override
    public SubPropertyDTO updateSubProperty(String propertyId, SubPropertyDTO subPropertyDTO) {
        SaleDTO saleDTO = saleService.findByIdSubPropertyId(subPropertyDTO.getId());
        if (saleDTO != null) {
            logger.error("SubProperty with  Id: " + subPropertyDTO.getId() + "is already sold. It is not possible update it.");
            throw new InternalErrorException("SubProperty with  Id: " + subPropertyDTO.getId() + "is already sold. It is not possible update it.");

        }
        return propertyService.updateSubProperty(propertyId, subPropertyDTO);
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO) {
        Image imageSvg = propertyDTO.getImagePlan();
        if (imageSvg != null && imageSvg.getId() != null) {
            PropertyDTO propertyFound = propertyService.findById(propertyDTO.getId());
            Image imagePlanFound = propertyFound.getImagePlan();
            if (imagePlanFound != null && !imageSvg.getId().equals(imagePlanFound.getId())) {
                List<SaleDTO> saleDTO = saleService.findAllByPropertyId(propertyDTO.getId());
                if (saleDTO.size() != 0) {
                    logger.error("Some SubProperties are sold, it is not possible updated from propertyId: " + propertyDTO.getId());
                    throw new InternalErrorException("Some SubProperties belong to Property with Id: " + propertyDTO.getId() + "are already sold. It is not possible update it.");
                } else {
                    logger.warn("Update the svg will remove the SubProperties are already created");
                    propertyDTO.setSubProperties(new ArrayList<>());
                }
            }
        }
        return propertyService.update(propertyDTO);
    }

    @Override
    public void deleteSubProperty(String propertyId, String subPropertyId) {
        SaleDTO saleDTO = saleService.findByIdSubPropertyId(subPropertyId);
        if (saleDTO != null) {
            logger.error("SubProperty with  Id: " + subPropertyId + "is already sold. It is not possible delete it.");
            throw new InternalErrorException("SubProperty with  Id: " + subPropertyId + "is already sold. It is not possible delete it.");
        }
        propertyService.deleteSubProperty(propertyId, subPropertyId);
    }

    private SubPropertyDTO getSubPropertyById(String subPropertyId, List<SubPropertyDTO> subPropertyDTOList) {
        return subPropertyDTOList.stream()
                .filter(subPropertyDTO -> subPropertyDTO.getId().equals(subPropertyId))
                .findFirst()
                .orElseThrow(()-> new DataNotFoundException("Unable to get SubProperty with Id: " + subPropertyId));
    }
}
