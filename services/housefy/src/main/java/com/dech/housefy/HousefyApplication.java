package com.dech.housefy;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class HousefyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousefyApplication.class, args);
    }

}
