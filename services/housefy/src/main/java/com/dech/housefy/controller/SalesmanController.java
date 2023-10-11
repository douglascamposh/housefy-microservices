package com.dech.housefy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dech.housefy.dto.CSVUploadDTO;
import com.dech.housefy.dto.SalesmanDTO;
import com.dech.housefy.service.ISalesmanService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(SalesmanController.BASE_CTRL_URL)
public class SalesmanController {
    public static final String BASE_CTRL_URL = "api/v1/saleman";
    private static final Logger logger = LoggerFactory.getLogger(SalesmanController.class);
    private final ISalesmanService salesmanService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<SalesmanDTO> uploadFile(HttpServletRequest request, @ModelAttribute CSVUploadDTO csvFileDTO) {
        logger.info("uploadCSVFile starting... filename {}", csvFileDTO.getFileName());
        return salesmanService.uploadCsvSalesmen(csvFileDTO.getFile());
    }
}
