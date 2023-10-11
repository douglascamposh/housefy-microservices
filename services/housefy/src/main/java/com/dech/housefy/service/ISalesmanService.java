package com.dech.housefy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dech.housefy.dto.SalesmanDTO;

public interface ISalesmanService {

    List<SalesmanDTO> uploadCsvSalesmen(MultipartFile file);
    
}
