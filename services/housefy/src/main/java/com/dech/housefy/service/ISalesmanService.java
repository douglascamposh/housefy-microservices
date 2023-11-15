package com.dech.housefy.service;

import java.util.List;

import com.dech.housefy.domain.Salesman;
import org.springframework.web.multipart.MultipartFile;

import com.dech.housefy.dto.SalesmanDTO;

public interface ISalesmanService {

    List<SalesmanDTO> uploadCsvSalesmen(MultipartFile file);
    SalesmanDTO createSalesman(SalesmanDTO salesman);
    SalesmanDTO getSalesmanById(String id);
    List<SalesmanDTO> getAllSalesman();
    SalesmanDTO updateSalesman(SalesmanDTO salesman, String id);
}
