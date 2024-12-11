package com.inditex.prices.dto;

import com.inditex.prices.model.Currency;
import com.inditex.prices.model.Price;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class PriceDto {
    /**
     * The price ID.
     */
    private Long priceId;

    /**
     * The ID of the brand associated to the price tariff.
     */
    private Long brandId;

    /**
     * The start date for which the price tariff applies.
     */
    private Instant startDate;

    /**
     * The end date for which the price tariff applies.
     */
    private Instant endDate;

    /**
     * The ID of the product associated to the price tariff.
     */
    private Long productId;

    /**
     * The price tariff priority used for resolving conflicts between different price tariffs.
     * The higher the value, the more priority has the price tariffs over others applicable price tariffs.
     */
    private int priority;

    /**
     * The final price amount (PVP).
     */
    private Double amount;

    /**
     * The currency ISO code.
     */
    private Currency currency;

    public static PriceDto fromEntity(Price price) {
        return PriceDto.builder()
                .priceId(price.getId())
                .productId(price.getProduct().getId())
                .brandId(price.getBrand().getId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .amount(price.getPrice())
                .currency(price.getCurrency())
                .priority(price.getPriority())
                .build();
    }
}
