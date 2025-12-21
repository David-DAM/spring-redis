package com.davinchicoder.spring.redis.infrastructure.scheduler;

import com.davinchicoder.spring.redis.domain.Prices;
import org.springframework.http.MediaType;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://", accept = MediaType.APPLICATION_JSON_VALUE)
public interface PricesClient {

    @GetExchange("/prices")
    Prices getPrices();
}
