package com.dech.housefy.service;

import com.dech.housefy.dto.SaleDTO;
import com.dech.housefy.dto.SoldPropertyFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISaleService {
    SoldPropertyFormDTO create(SoldPropertyFormDTO soldPropertyFormDTO);
    List<SaleDTO> findAllByPropertyId(String propertyId);
    SaleDTO findByIdSubPropertyId(String subPropertyId);
}
