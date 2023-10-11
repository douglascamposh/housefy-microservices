package com.dech.housefy.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dech.housefy.domain.Salesman;
import com.dech.housefy.dto.SalesmanCreateDTO;
import com.dech.housefy.dto.SalesmanDTO;
import com.dech.housefy.repository.ISalesmanRepository;
import com.dech.housefy.service.ISalesmanService;
import com.dech.housefy.utils.Utils;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SalesmanImpl implements ISalesmanService{
    
    private static final Logger logger = LoggerFactory.getLogger(SalesmanImpl.class);

    private final ModelMapper modelMapper;
    private final ISalesmanRepository salesmanRepository;

    @Override
    public List<SalesmanDTO> uploadCsvSalesmen(MultipartFile file) {
        List<SalesmanCreateDTO> salesmenCreateDTO = Utils.convertFileCsvToModel(file, SalesmanCreateDTO.class);

        List<Salesman> salesmen = salesmenCreateDTO.stream().map(salesmanDTO -> modelMapper.map(salesmanDTO, Salesman.class)).collect(Collectors.toList());
        List<Salesman> savedSalesmen = salesmen.stream()
                .filter(salesman -> !isDuplicate(salesman))
                .map(salesmanRepository::save)
                .collect(Collectors.toList());

        List<SalesmanDTO> salesmenDTO = savedSalesmen.stream().map(salesman -> modelMapper.map(salesman, SalesmanDTO.class)).collect(Collectors.toList());
        logger.info("Successfully created " + salesmenDTO.size() + " salesmen.");
        return salesmenDTO;
    }

    private boolean isDuplicate(Salesman salesman){
        boolean isDuplicate =  salesmanRepository.existsByNameAndLastName(salesman.getName(), salesman.getLastName());
        if (isDuplicate) {
            logger.info("Filtered out duplicate Saleman: " + salesman.getName() + " " + salesman.getLastName());
        }
        return isDuplicate;
    }
}
