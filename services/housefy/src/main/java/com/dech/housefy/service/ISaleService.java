package com.dech.housefy.service;

import com.dech.housefy.dto.SoldPropertyFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface ISaleService {
    SoldPropertyFormDTO create(SoldPropertyFormDTO soldPropertyFormDTO);
}
