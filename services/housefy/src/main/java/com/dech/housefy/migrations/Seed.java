package com.dech.housefy.migrations;


import com.dech.housefy.controller.PropertyController;
import com.dech.housefy.dto.SignupRequest;
import com.dech.housefy.dto.UserDTO;
import com.dech.housefy.enums.RoleEnums;
import com.dech.housefy.service.IUserService;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id="client-initializer", order = "001", author = "douglas")
public class Seed {
    private final MongoTemplate mongoTemplate;
    private final IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(Seed.class);

    public Seed(MongoTemplate mongoTemplate, IUserService userService) {
        this.mongoTemplate = mongoTemplate;
        this.userService = userService;
    }


    @Execution
    public void changeSet() {
        String emailAdmin =  "admin@admin";
        SignupRequest userSignUp = new SignupRequest();
        userSignUp.setName("Admin");
        userSignUp.setEmail(emailAdmin);
        userSignUp.setLastName("Admin");
        userSignUp.setPhoneNumber("12345678");
        userSignUp.setPassword("123456789A.");//Todo set this field on secrets
        userService.signup(userSignUp);
        userService.addRoleUser(emailAdmin, RoleEnums.ROLE_ADMIN.toString());

    }

    @RollbackExecution
    public void rollback() {

    }
}
