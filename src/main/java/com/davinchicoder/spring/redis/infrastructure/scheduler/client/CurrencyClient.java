package com.davinchicoder.spring.redis.infrastructure.scheduler.client;

import com.davinchicoder.spring.redis.infrastructure.scheduler.client.dto.CurrencyApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://api.appnexus.com", accept = MediaType.APPLICATION_JSON_VALUE)
public interface CurrencyClient {

    @GetExchange("/currency?show_rate=true")
    CurrencyApiResponse getCurrencies();
}
