package com.dech.housefy.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Document("token")
public class Token {
    @Id
    private String id;
    @NotBlank
    private String userId;
    @NotBlank
    private String token;

}
