package com.dech.housefy.controller;

import com.dech.housefy.domain.AdminParam;
import com.dech.housefy.dto.AdminParamFormDTO;
import com.dech.housefy.dto.PropertyDTO;
import com.dech.housefy.service.IAdminParamsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(AdminParamsController.BASE_CTRL_URL)
public class AdminParamsController {

    public static final String BASE_CTRL_URL = "api/v1/config";
    private static final Logger logger = LoggerFactory.getLogger(AdminParamsController.class);
    private final IAdminParamsService adminParamsService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminParam> getAdminParamsConfig() {
        return adminParamsService.findAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AdminParam save(@Valid @RequestBody AdminParamFormDTO adminParam) {
        logger.info("Saving admin param with key name: " + adminParam.getParamKey());
        return adminParamsService.saveOrUpdate(adminParam);
    }
}
