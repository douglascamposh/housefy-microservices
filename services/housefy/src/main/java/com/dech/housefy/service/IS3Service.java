package com.dech.housefy.service;

import com.dech.housefy.dto.ImageUploadDTO;

public interface IS3Service {
    String uploadImageProperties(String propertyId ,ImageUploadDTO imageUploadDTO);
}
