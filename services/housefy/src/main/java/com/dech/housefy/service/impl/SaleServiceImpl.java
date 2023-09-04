package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.domain.Sale;
import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.dto.SoldPropertyFormDTO;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.ICustomerRepository;
import com.dech.housefy.repository.ISaleRepository;
import com.dech.housefy.service.ISaleService;
import com.dech.housefy.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements ISaleService {
    private final ISaleRepository saleRepository;
    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    @Override
    public SoldPropertyFormDTO create(SoldPropertyFormDTO soldForm) {
        Optional<Sale> saleFound = saleRepository.findBySubPropertyId(soldForm.getSubPropertyId());
        if (saleFound.isPresent()) {
            logger.error("It could not save Sale with Sub property Id: " + soldForm.getSubPropertyId() + " because it was created before.");
            throw new DuplicateDataException("Sale with sub property Id: " + soldForm.getSubPropertyId() + " it was registered before.");
        }
        Customer customerToSave = modelMapper.map(soldForm.getCustomer(), Customer.class);
        customerToSave.setBirthDate(Utils.convertStringToDate(soldForm.getCustomer().getBirthDate()));
        Customer newCustomer = customerRepository.save(customerToSave);
        logger.info("Customer created with Id: " + newCustomer.getId());
        Sale sale = modelMapper.map(soldForm, Sale.class);
        sale.setCustomerId(newCustomer.getId());
        Sale newSale = saleRepository.save(sale);
        logger.info("Sale created with Sub Property Id: " + newSale.getSubPropertyId());
        SoldPropertyFormDTO response = modelMapper.map(newSale, SoldPropertyFormDTO.class);
        response.setCustomer(modelMapper.map(newCustomer, CustomerDTO.class));

        return response;
    }
}
