package com.davinchicoder.spring.redis.infrastructure.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(PricesClient.class)
public class HttpClientConfig {
}
