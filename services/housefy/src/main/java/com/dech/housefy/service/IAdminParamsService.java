package com.dech.housefy.service;

import com.dech.housefy.domain.AdminParam;
import com.dech.housefy.dto.AdminParamFormDTO;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.List;

public interface IAdminParamsService {
    List<AdminParam> findAll();
    AdminParam findByKeyName(String keyName);
    AdminParam save(AdminParamFormDTO adminParam);
}
