package com.dech.housefy;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Slf4j
@Configuration
public class ApplicationConfig {
    @Value("${aws.access_key_id}")
    private String accessKeyId;
    @Value("${aws.secret_access_key}")
    private String secretKey;
    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public S3Client s3Client() {
        log.info("***********************AWS S3*************************");
        if (accessKeyId.equals(null) || accessKeyId.equals("")) {
            log.error("accessKeyId is null");
        }
        for (String s : accessKeyId.split("")) {
            log.info("{}", s);
        }
        log.info("accessKeyId: {}", accessKeyId);
        log.info("secretKey: {}", secretKey);
        log.info("Region: {}", region);
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        S3Client s3client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .build();
        return s3client;
    }
}
