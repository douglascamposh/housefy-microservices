package com.dech.housefy.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.dech.housefy.domain.AdminParam;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class Utils {
    public static String getUniqueKeyName(String fileName) {
        String newCleanName;
        String newCleanNameDecoded;
        newCleanName = FilenameUtils.getName(fileName.trim());
        try{
            newCleanNameDecoded = URLDecoder.decode(newCleanName, "UTF-8");
            newCleanName = newCleanNameDecoded.replaceAll("\\s", "");
        }catch(Exception e){
            log.error("getUniqueKeyName - error decoding utf so get fallback name" + newCleanName);
        }
        newCleanName = newCleanName.replaceAll("[^a-zA-Z0-9-.]", "-");
        newCleanName = newCleanName.toLowerCase();
        newCleanName = newCleanName.replaceAll("___", "---");
        long unixTime = System.currentTimeMillis();
        newCleanName = unixTime + "-" + newCleanName;
        return newCleanName;
    }

    public static LocalDate convertStringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static <T> HashMap<String, String> getFields(T dto) {
        HashMap<String, String> fields = new HashMap<>();
        for (Field field : dto.getClass().getDeclaredFields()) {
            try {
              field.setAccessible(true);
              if(field.get(dto) != null && !field.get(dto).toString().isEmpty())
                 fields.put(field.getName(), field.get(dto).toString());
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
        }
        return fields;
    }
    public static Long getCurrentDate() {
        return LocalDateTime.now().toInstant(java.time.ZoneOffset.UTC).toEpochMilli()/1000;
    }

    public static Long addDaysToDate(Long days) {
        return LocalDateTime.now().plusDays(days).toInstant(java.time.ZoneOffset.UTC).toEpochMilli()/1000;
    }

    public static Long getValueFromParams(Optional<AdminParam> adminParam, String key, String defaultValue) {
        AdminParam param = adminParam.orElse(AdminParam.builder().paramKey(key).paramValue(defaultValue).build());
        return Long.parseLong(param.getParamValue());
    }
    
    public static <T> List<T> convertFileCsvToModel(MultipartFile file, Class<T> classType) {
        List<T> models;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(classType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            models = csvToBean.parse();
        } catch (Exception exception) {
            log.error("error parsing csv file {} ", exception);
            throw new IllegalArgumentException(exception.getCause().getMessage());
        }
        return models;
    }
}
