package com.dech.housefy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequest {
    private String email;
    private String password;
}
