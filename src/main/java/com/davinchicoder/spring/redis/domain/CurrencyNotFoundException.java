package com.davinchicoder.spring.redis.domain;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String code) {
        super("The currency " + code + " was not found.");
    }
}
