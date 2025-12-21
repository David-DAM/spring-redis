package com.davinchicoder.spring.redis.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PricesLoaderScheduler {

    private final PricesClient pricesClient;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "loadPrices", lockAtMostFor = "PT30S", lockAtLeastFor = "PT5S")
    public void loadPrices() {
        log.info("Loading prices");
        loadData();
        log.info("Prices loaded");
    }

    @Retryable(maxRetries = 4, delay = 500L, multiplier = 2)
    private void loadData() {
        pricesClient.getPrices();
    }

}
