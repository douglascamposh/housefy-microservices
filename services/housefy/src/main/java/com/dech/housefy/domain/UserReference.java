package com.dech.housefy.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_reference")
public class UserReference extends BaseUser {
    @NotBlank
    private String relationship;
}
