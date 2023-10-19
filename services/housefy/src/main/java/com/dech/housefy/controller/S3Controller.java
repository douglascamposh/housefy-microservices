package com.dech.housefy.controller;

import com.dech.housefy.dto.ImageResponseDTO;
import com.dech.housefy.service.IS3Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(S3Controller.BASE_CTRL_URL)
public class S3Controller {
    public static final String BASE_CTRL_URL = "api/v1/s3";
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final IS3Service s3Service;

    @GetMapping("/images/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImageResponseDTO getImageS3(@Valid @NotNull @PathVariable("id") String id) {
        logger.info("Getting image path from S3 imageId: " + id);
        return s3Service.getImage(id);
    }
}
