package com.dech.housefy.service.impl;

import com.dech.housefy.constants.Constants;
import com.dech.housefy.domain.AdminParam;
import com.dech.housefy.domain.Customer;
import com.dech.housefy.domain.Sale;
import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.dto.SaleDTO;
import com.dech.housefy.dto.SoldPropertyFormDTO;
import com.dech.housefy.enums.StateSales;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.error.InternalErrorException;
import com.dech.housefy.repository.IAdminParamRepository;
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
    private final IAdminParamRepository adminParamRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    @Override
    public SoldPropertyFormDTO create(SoldPropertyFormDTO soldForm) {
        if (soldForm.getStatus().equals(StateSales.SOLD.name()) || soldForm.getStatus().equals(StateSales.RESERVED.name())) {
            Optional<Sale> saleFound = saleRepository.findBySubPropertyId(soldForm.getSubPropertyId());
            if (saleFound.isPresent()) {
                logger.error("It could not save Sale with Sub property Id: " + soldForm.getSubPropertyId() + " because it was created before.");
                throw new DuplicateDataException("Sale with sub property Id: " + soldForm.getSubPropertyId() + " it was registered before.");
            }
            Sale sale = modelMapper.map(soldForm, Sale.class);
            sale.setId(null);
            sale.setCustomerId(soldForm.getCustomer().getId());
            sale.setBalance(calculateBalance(soldForm.getTotal(), soldForm.getOnAccount()));
            sale.setCreatedAt(Utils.getCurrentDate());
            if (sale.getStatus().equals(StateSales.RESERVED.name())) {
                Long expirationDate = Utils.getValueFromParams(adminParamRepository.findAdminParamByParamKey(Constants.EXPIRATION_DATE_SALES), Constants.EXPIRATION_DATE_SALES, Constants.DEFAULT_EXPIRATION_DATE_SALES);
                sale.setReservationExpiresDate(expirationDate);
            }
            Sale newSale = saleRepository.save(sale);
            logger.info("Sale created with Sub Property Id: " + newSale.getSubPropertyId());
            return modelMapper.map(newSale, SoldPropertyFormDTO.class);
        } else {
            throw new InternalErrorException("Status should be SOLD or RESERVED");
        }
    }

    @Override
    public List<SaleDTO> findAllByPropertyId(String propertyId) {
        return saleRepository.findAllByPropertyId(propertyId).stream().map(sale -> modelMapper.map(sale, SaleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public SaleDTO findByIdSubPropertyId(String subPropertyId) {
        Optional<Sale> saleFound = saleRepository.findBySubPropertyId(subPropertyId);
        return saleFound.map(sale -> modelMapper.map(sale, SaleDTO.class)).orElse(null);
    }

    private Float calculateBalance(Float total, Float onAccount) {
        if (onAccount > total) {
            logger.error("OnAccount field should not be greater than total");
            throw new RuntimeException("On account field it should not be greater than total");
        }
        return total - onAccount;
    }
}
