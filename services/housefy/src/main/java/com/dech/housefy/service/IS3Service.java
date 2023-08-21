package com.dech.housefy.service;

import com.dech.housefy.dto.ImageResponseDTO;
import com.dech.housefy.dto.ImageUploadDTO;

public interface IS3Service {
    ImageResponseDTO uploadImageProperties(ImageUploadDTO imageUploadDTO);
    void deleteImageProperties(String imageId);
}
