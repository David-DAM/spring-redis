package com.davinchicoder.spring.redis.domain;

import lombok.Data;

@Data
public class CurrencyEvent {

    private String code;
    private CurrencyEventType type;
    private String name;
    private String symbol;

}
