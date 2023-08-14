package com.dech.housefy.utils;

import java.net.URLDecoder;

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
}
