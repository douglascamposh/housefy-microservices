package com.dech.housefy.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dech.housefy.domain.Customer;
import com.dech.housefy.dto.CustomerDTO;
import com.dech.housefy.dto.UserReferenceDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.error.InternalErrorException;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dech.housefy.domain.Salesman;
import com.dech.housefy.dto.SalesmanCreateDTO;
import com.dech.housefy.dto.SalesmanDTO;
import com.dech.housefy.repository.ISalesmanRepository;
import com.dech.housefy.service.ISalesmanService;
import com.dech.housefy.utils.Utils;

import java.util.Objects;
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

    @Override
    public SalesmanCreateDTO createSalesman(SalesmanDTO salesmanDTO) {
        boolean exist = salesmanRepository.existsByNameAndLastName(salesmanDTO.getName(),salesmanDTO.getLastName());
        if (exist == false) {
            Salesman salesman = modelMapper.map(salesmanDTO, Salesman.class);
            var salesmanCreated = salesmanRepository.save(salesman);
            var newSalesmanDTO = modelMapper.map(salesmanCreated, SalesmanCreateDTO.class);
            return newSalesmanDTO;
        }
        throw new DuplicateDataException("There is a salesman created with the name: " + salesmanDTO.getName() + " " + salesmanDTO.getLastName());
    }

    @Override
    public SalesmanDTO getSalesmanById(String id) {
        var salesman = this.salesmanRepository.findById(id);
        if (!salesman.isEmpty()){
            var salesmanDTO   = modelMapper.map(salesman, SalesmanDTO.class);
            return salesmanDTO;
        } else {
            throw new DataNotFoundException("Unable to get Salesman with Id: " + id);
        }

    }

    @Override
    public List<SalesmanDTO> getAllSalesman() {
        var salesman = this.salesmanRepository.findAll();
        List<SalesmanDTO> salesmanDTO = new ArrayList<SalesmanDTO>();
         salesman.forEach(x -> {
             salesmanDTO.add(modelMapper.map(x, SalesmanDTO.class));
         });
         return salesmanDTO;
    }

    @Override
    public SalesmanDTO updateSalesman(SalesmanDTO salesman) {
        if(salesman.getId().isEmpty()) throw new DataNotFoundException("The data can't be null" );

        var salesmanToUpdate = this.salesmanRepository.findById(salesman.getId()).orElseThrow();
        if (!salesmanToUpdate.getId().isEmpty()) {
            salesmanToUpdate = modelMapper.map(salesman, Salesman.class);
            this.salesmanRepository.save(salesmanToUpdate);
            var salesmanDTO = modelMapper.map(salesmanToUpdate, SalesmanDTO.class);
            return salesmanDTO;
        } else {
            throw new DataNotFoundException("Unable to update Salesman with Id: " + salesman.getId());
        }
    }

    private boolean isDuplicate(Salesman salesman){
        boolean isDuplicate =  salesmanRepository.existsByNameAndLastName(salesman.getName(), salesman.getLastName());
        if (isDuplicate) {
            logger.info("Filtered out duplicate Saleman: " + salesman.getName() + " " + salesman.getLastName());
        }
        return isDuplicate;
    }
}
