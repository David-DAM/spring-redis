package com.davinchicoder.spring.redis.infrastructure.scheduler;

import com.davinchicoder.spring.redis.application.CurrencyService;
import com.davinchicoder.spring.redis.domain.Currency;
import com.davinchicoder.spring.redis.domain.CurrencyEvent;
import com.davinchicoder.spring.redis.domain.CurrencyEventType;
import com.davinchicoder.spring.redis.infrastructure.cache.event.producer.CurrencyPubSubProducer;
import com.davinchicoder.spring.redis.infrastructure.cache.event.producer.CurrencyStreamProducer;
import com.davinchicoder.spring.redis.infrastructure.scheduler.client.CurrencyClient;
import com.davinchicoder.spring.redis.infrastructure.scheduler.client.CurrencyClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyLoaderScheduler {

    private final CurrencyClient client;
    private final CurrencyClientMapper mapper;
    private final CurrencyService service;
    private final CurrencyStreamProducer streamProducer;
    private final CurrencyPubSubProducer pubSubProducer;

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(name = "loadCurrencies", lockAtMostFor = "PT30S", lockAtLeastFor = "PT5S")
    public void loadPrices() {
        log.info("Loading currencies");
        processData();
        log.info("Currencies loaded");
    }

    @Retryable(maxRetries = 4, delay = 500L, multiplier = 2)
    private void processData() {
        List<Currency> currencies = loadAndStoreCurrencies();
        publishEvents(currencies);
        pushNotifications(currencies);
    }

    private List<Currency> loadAndStoreCurrencies() {
        List<Currency> currencies = client.getCurrencies().response().currencies().stream().map(mapper).toList();
        service.upsertAll(currencies);
        return currencies;
    }

    private void publishEvents(List<Currency> currencies) {

        currencies.stream()
                .map(currency -> new CurrencyEvent(
                        UUID.randomUUID().toString(),
                        currency.getCode(),
                        CurrencyEventType.CURRENCY_UPDATED.name(),
                        Instant.now().toEpochMilli()
                ))
                .forEach(streamProducer::publishCurrencyEvent);
    }

    private void pushNotifications(List<Currency> currencies) {
        currencies.stream()
                .max(Comparator.comparing(Currency::getRatePerUsd))
                .map(currency -> new CurrencyEvent(
                        UUID.randomUUID().toString(),
                        currency.getCode(),
                        CurrencyEventType.CURRENCY_MAX_PRICE_UPDATED.name(),
                        Instant.now().toEpochMilli()
                ))
                .ifPresent(pubSubProducer::publishCurrencyEvent);

        currencies.stream()
                .min(Comparator.comparing(Currency::getRatePerUsd))
                .map(currency -> new CurrencyEvent(
                        UUID.randomUUID().toString(),
                        currency.getCode(),
                        CurrencyEventType.CURRENCY_MIN_PRICE_UPDATED.name(),
                        Instant.now().toEpochMilli()
                ))
                .ifPresent(pubSubProducer::publishCurrencyEvent);
    }

}
