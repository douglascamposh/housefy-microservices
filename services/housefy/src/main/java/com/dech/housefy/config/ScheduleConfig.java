package com.dech.housefy.config;

import com.dech.housefy.service.ISaleService;
import com.dech.housefy.service.impl.SaleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@RequiredArgsConstructor
@Configuration
public class ScheduleConfig {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
    private final ISaleService saleService;
    @Scheduled(cron = "0 5 0 * * ?")
    public void scheduleFixedDelayTask() {
        logger.info("Running every day at midnight");
        saleService.deleteExpiredSales();
    }
}
