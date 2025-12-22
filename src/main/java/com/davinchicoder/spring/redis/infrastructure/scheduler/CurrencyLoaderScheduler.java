package com.davinchicoder.spring.redis.infrastructure.scheduler;

import com.davinchicoder.spring.redis.application.CurrencyService;
import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.domain.CurrencyEventType;
import com.davinchicoder.spring.redis.infrastructure.cache.repository.CurrencyStreamRepository;
import com.davinchicoder.spring.redis.infrastructure.scheduler.client.CurrencyClient;
import com.davinchicoder.spring.redis.infrastructure.scheduler.client.CurrencyClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyLoaderScheduler {

    private final CurrencyClient client;
    private final CurrencyClientMapper mapper;
    private final CurrencyService service;
    private final CurrencyStreamRepository streamRepository;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "loadCurrencies", lockAtMostFor = "PT30S", lockAtLeastFor = "PT5S")
    public void loadPrices() {
        log.info("Loading currencies");
        processData();
        log.info("Currencies loaded");
    }

    @Retryable(maxRetries = 4, delay = 500L, multiplier = 2)
    private void processData() {
        List<Currency> currencies = loadAndStoreCurrencies();
//        publishEvents(currencies);
    }

    private List<Currency> loadAndStoreCurrencies() {
        List<Currency> currencies = client.getCurrencies().response().currencies().stream().map(mapper).toList();
        service.upsertAll(currencies);
        return currencies;
    }

    private void publishEvents(List<Currency> currencies) {
        currencies.stream()
                .max(Comparator.comparing(Currency::getRatePerUsd))
                .ifPresent(currency -> streamRepository.publishCurrencyEvent(currency, CurrencyEventType.CURRENCY_MAX_PRICE_UPDATED.name()));

        currencies.stream()
                .min(Comparator.comparing(Currency::getRatePerUsd))
                .ifPresent(currency -> streamRepository.publishCurrencyEvent(currency, CurrencyEventType.CURRENCY_MIN_PRICE_UPDATED.name()));

        currencies.forEach(currency -> streamRepository.publishCurrencyEvent(currency, CurrencyEventType.CURRENCY_UPDATED.name()));
    }

}
