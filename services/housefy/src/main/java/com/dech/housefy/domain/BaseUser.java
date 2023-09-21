package com.dech.housefy.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class BaseUser {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
//    @CreatedDate
//    private LocalDateTime createdDate;

//    @CreatedBy
//    private String createdBy;
//
//    @LastModifiedDate
//    private LocalDateTime lastModifiedDate;
//
//    @LastModifiedBy
//    private String lastModifiedBy;
}
