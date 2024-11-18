package com.inditex.prices.dto;

import com.inditex.prices.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@Getter
@ToString
public class FindPriceTariffResponse {
    private Long productId;

    private Long brandId;

    private Long priceId;

    private Instant startDate;

    private Instant endDate;

    private Double price;

    private Currency currency;
}
