package com.davinchicoder.spring.redis.infrastructure.scheduler.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(CurrencyClient.class)
public class HttpClientConfig {
}
