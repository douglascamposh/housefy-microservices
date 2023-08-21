package com.dech.housefy.controller;

import com.dech.housefy.dto.ImageResponseDTO;
import com.dech.housefy.dto.ImageUploadDTO;
import com.dech.housefy.dto.PropertyDTO;
import com.dech.housefy.service.IPropertyService;
import com.dech.housefy.service.IS3Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(PropertyController.BASE_CTRL_URL)
public class PropertyController {

    public static final String BASE_CTRL_URL = "api/v1/properties";
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    private final IPropertyService propertyService;
    private final IS3Service s3Service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyDTO> findAll() {
        return propertyService.findAll();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PropertyDTO save(@Valid @RequestBody PropertyDTO property) {
        return propertyService.save(property);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PropertyDTO findById(@Valid @NotNull @PathVariable("id") String id) {
        return propertyService.findById(id);
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PropertyDTO update(@Valid @NotNull @PathVariable("id") String id, @Valid @RequestBody PropertyDTO property) {
        property.setId(id);
        return propertyService.update(property);
    }

    @GetMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "prueba docker 4";
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ImageResponseDTO imageUploadDTO(HttpServletRequest request, @ModelAttribute ImageUploadDTO imageUploadDTO) {
        logger.info("uploadListingImage starting... filename {}", imageUploadDTO.getFilename());
        return s3Service.uploadImageProperties(imageUploadDTO);
    }

    @DeleteMapping(value = "/delete/{imageId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImageS3(HttpServletRequest request, @NotNull @PathVariable("imageId") String imageId) {
        logger.info("deleting starting... filename {}", imageId);
        s3Service.deleteImageProperties(imageId);
    }
}
