package com.dech.housefy.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "admin_param")
public class AdminParam {
    @Id
    private String id;
    @NotBlank
    private String paramKey;
    @NotBlank
    private String paramValue;
}
