package com.dech.housefy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dech.housefy.dto.RoleCreateDTO;
import com.dech.housefy.dto.RoleDTO;
import com.dech.housefy.service.IRoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(RoleController.BASE_CTRL_URL)
public class RoleController {
    public static final String BASE_CTRL_URL = "api/v1/roles";
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final IRoleService roleService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDTO> findAll() {
        logger.info(BASE_CTRL_URL);
        return roleService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO save(@RequestBody RoleCreateDTO role) {
        return roleService.save(role);
    }
    

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO update(@Valid @NotNull @PathVariable("id") String id, @Valid @RequestBody RoleDTO role) {
        role.setId(id);
        return roleService.update(role);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @NotNull @PathVariable("id") String id){
        logger.info("the role with " + id + " id,  is deleted");
        roleService.delete(id);
    }
}
