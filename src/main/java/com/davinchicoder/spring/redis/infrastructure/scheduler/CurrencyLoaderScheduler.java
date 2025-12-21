package com.davinchicoder.spring.redis.infrastructure.scheduler;

import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyRedisRepository;
import com.davinchicoder.spring.redis.infrastructure.scheduler.client.CurrencyApiMapper;
import com.davinchicoder.spring.redis.infrastructure.scheduler.client.CurrencyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyLoaderScheduler {

    private final CurrencyClient client;
    private final CurrencyApiMapper mapper;
    private final CurrencyRedisRepository repository;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "loadCurrencies", lockAtMostFor = "PT30S", lockAtLeastFor = "PT5S")
    public void loadPrices() {
        log.info("Loading currencies");
        loadData();
        log.info("Currencies loaded");
    }

    @Retryable(maxRetries = 4, delay = 500L, multiplier = 2)
    private void loadData() {
        List<Currency> currencies = client.getCurrencies().stream().map(mapper).toList();
        repository.saveAllCurrencies(currencies);
    }

}
