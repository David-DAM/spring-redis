package com.davinchicoder.spring.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyEvent {

    private String eventId;
    private String currencyId;
    private String type;
    private Long timestamp;

}
