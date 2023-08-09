package com.dech.housefy.controller;

import com.dech.housefy.domain.Property;
import com.dech.housefy.service.IPropertyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(PropertyController.BASE_CTRL_URL)
public class PropertyController {

    public static final String BASE_CTRL_URL = "api/v1/properties";
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    private final IPropertyService propertyService;

    @GetMapping()
    public List<Property> findAll() {
        return propertyService.findAll();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Property save(@RequestBody Property property) {
        return propertyService.save(property);
    }

    @GetMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "prueba docker";
    }
}
