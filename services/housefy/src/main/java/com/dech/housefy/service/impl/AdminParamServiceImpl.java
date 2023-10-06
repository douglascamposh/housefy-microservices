package com.dech.housefy.service.impl;

import com.dech.housefy.domain.AdminParam;
import com.dech.housefy.dto.AdminParamFormDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.IAdminParamRepository;
import com.dech.housefy.service.IAdminParamsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminParamServiceImpl implements IAdminParamsService {

    private final IAdminParamRepository adminParamRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(AdminParamServiceImpl.class);
    @Override
    public List<AdminParam> findAll() {
        return adminParamRepository.findAll();
    }

    @Override
    public AdminParam findByKeyName(String keyName) {
        return adminParamRepository.findAdminParamByParamKey(keyName).orElseThrow(() -> new DataNotFoundException("The param with name: " + keyName + "was not found"));
    }

    @Override
    public AdminParam saveOrUpdate(AdminParamFormDTO adminParam) {
        AdminParam param = modelMapper.map(adminParam, AdminParam.class);
        Optional<AdminParam> optionalAdminParam = adminParamRepository.findAdminParamByParamKey(adminParam.getParamKey());
        if (!optionalAdminParam.isPresent()) {
            logger.info("create admin param");
            param.setId(new ObjectId().toString());
        } else {
            logger.info("update admin param");
            param.setId(optionalAdminParam.get().getId());
        }
        return adminParamRepository.save(param);
    }
}
