package com.dech.housefy.controller;

import com.dech.housefy.dto.SoldPropertyFormDTO;
import com.dech.housefy.service.ISaleService;
import com.dech.housefy.service.impl.SaleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(SaleController.BASE_CTRL_URL)
public class SaleController {
    public static final String BASE_CTRL_URL = "api/v1/sales";
    private static final Logger logger = LoggerFactory.getLogger(SaleController.class);
    private final ISaleService saleService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SoldPropertyFormDTO saveSoldProperty(@Valid @RequestBody SoldPropertyFormDTO soldPropertyFormDTO) {
        logger.info("Saving new Customer and selling a property with sub propertyId: {}", soldPropertyFormDTO.getSubPropertyId());
        return saleService.create(soldPropertyFormDTO);
    }
}
