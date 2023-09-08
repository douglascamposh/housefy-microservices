package com.dech.housefy.service.impl;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.domain.Sale;
import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.dto.SaleDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements ISaleService {
    private final ISaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    @Override
    public SoldPropertyFormDTO create(SoldPropertyFormDTO soldForm) {
        Optional<Sale> saleFound = saleRepository.findBySubPropertyId(soldForm.getSubPropertyId());
        if (saleFound.isPresent()) {
            logger.error("It could not save Sale with Sub property Id: " + soldForm.getSubPropertyId() + " because it was created before.");
            throw new DuplicateDataException("Sale with sub property Id: " + soldForm.getSubPropertyId() + " it was registered before.");
        }
        Sale sale = modelMapper.map(soldForm, Sale.class);
        sale.setId(null);
        sale.setCustomerId(soldForm.getCustomer().getId());
        sale.setBalance(calculateBalance(soldForm.getTotal(), soldForm.getOnAccount()));
        Sale newSale = saleRepository.save(sale);
        logger.info("Sale created with Sub Property Id: " + newSale.getSubPropertyId());
        return modelMapper.map(newSale, SoldPropertyFormDTO.class);
    }

    @Override
    public List<SaleDTO> findAllByPropertyId(String propertyId) {
        return saleRepository.findAllByPropertyId(propertyId).stream().map(sale -> modelMapper.map(sale, SaleDTO.class)).collect(Collectors.toList());
    }

    private Float calculateBalance(Float total, Float onAccount) {
        if (onAccount > total) {
            logger.error("OnAccount field should not be greater than total");
            throw new RuntimeException("On account field it should not be greater than total");
        }
        return total - onAccount;
    }
}
