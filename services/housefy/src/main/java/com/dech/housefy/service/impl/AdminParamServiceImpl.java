package com.dech.housefy.service.impl;

import com.dech.housefy.domain.AdminParam;
import com.dech.housefy.dto.AdminParamFormDTO;
import com.dech.housefy.error.DataNotFoundException;
import com.dech.housefy.error.DuplicateDataException;
import com.dech.housefy.repository.IAdminParamRepository;
import com.dech.housefy.service.IAdminParamsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminParamServiceImpl implements IAdminParamsService {

    private final IAdminParamRepository adminParamRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<AdminParam> findAll() {
        return adminParamRepository.findAll();
    }

    @Override
    public AdminParam findByKeyName(String keyName) {
        return adminParamRepository.findByKey(keyName).orElseThrow(() -> new DataNotFoundException("The param with name: " + keyName + "was not found"));
    }

    @Override
    public AdminParam save(AdminParamFormDTO adminParam) {
        Optional<AdminParam> optionalAdminParam = adminParamRepository.findByKey(adminParam.getParamKey());
        if (!optionalAdminParam.isPresent()) {
            throw new DuplicateDataException("The value: " + adminParam.getParamKey() + "already exist.");
        }
        AdminParam param = modelMapper.map(adminParam, AdminParam.class);
        return adminParamRepository.save(param);
    }
}
