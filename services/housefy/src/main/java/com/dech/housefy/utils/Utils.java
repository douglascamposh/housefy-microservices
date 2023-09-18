package com.dech.housefy.utils;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

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
              if(field.get(dto) != null && field.get(dto).toString() != "")
                 fields.put(field.getName(), field.get(dto).toString());
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
        }
        return fields;
    }
}
