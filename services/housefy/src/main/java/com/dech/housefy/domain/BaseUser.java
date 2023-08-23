package com.dech.housefy.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class BaseUser {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank String email;
    @NotBlank
    private String phoneNumber;
}
