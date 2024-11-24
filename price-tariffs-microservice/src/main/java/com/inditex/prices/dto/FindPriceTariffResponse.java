package com.inditex.prices.dto;

import com.inditex.prices.model.Currency;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class FindPriceTariffResponse {
    private Long productId;

    private Long brandId;

    private Long priceId;

    private Instant startDate;

    private Instant endDate;

    private Double price;

    private Currency currency;

    public static FindPriceTariffResponse fromPriceDto(PriceDto price) {
        return FindPriceTariffResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceId(price.getPriceId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getAmount())
                .currency(price.getCurrency())
                .build();
    }
}
