package com.dech.housefy.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class ImageUploadDTO {
    private MultipartFile image;
    private String filename;
    private String entityType;
    private String entityId;
}
