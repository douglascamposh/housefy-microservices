package com.dech.housefy.service.impl;

import com.dech.housefy.controller.PropertyController;
import com.dech.housefy.dto.ImageUploadDTO;
import com.dech.housefy.error.InternalErrorException;
import com.dech.housefy.service.IS3Service;
import com.dech.housefy.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements IS3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.properties-folder}")
    private String propertiesFolder;
    private static final Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    private final S3Client s3client;
    @Override
    public String uploadImageProperties(String propertyId, ImageUploadDTO imageUploadDTO) {
        imageUploadDTO.setEntityId(propertyId);
        String filename = Utils.getUniqueKeyName(imageUploadDTO.getFilename().trim());
        String s3PathProperties = propertiesFolder + "/" + imageUploadDTO.getEntityId();
        logger.info("uploadImageProperties - s3PathProperties: " + s3PathProperties);
        uploadImage(bucketName, filename, s3PathProperties, imageUploadDTO.getImage());
        return null;
    }

    private String uploadImage(String customBucketName, String keyName, String foldersPath, MultipartFile file) {
        try {
            if(customBucketName.isEmpty()){
                throw new InternalErrorException("Bucket name is empty, please contact IT");
            }
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(customBucketName)
                    .key(foldersPath + "/" + keyName)
                    .contentType("image/jpg")
                    .acl("public-read")
                    .build();
            PutObjectResponse response = s3client.putObject(request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            String url = response.eTag();
            return url;
        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());
            throw new InternalErrorException("Cannot upload image - msg:"+ ioe.getMessage());
        } catch (AwsServiceException ase) {
            logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:" + ase.getMessage());
            logger.error("HTTP Status Code: " + ase.statusCode());
            logger.error("AWS Error Code: " + ase.awsErrorDetails().errorCode());
            logger.error("Error message: " + ase.awsErrorDetails().errorMessage());
            throw ase;
        } catch (SdkClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
    }
}
