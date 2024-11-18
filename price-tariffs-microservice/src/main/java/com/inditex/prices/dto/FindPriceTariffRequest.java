package com.inditex.prices.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FindPriceTariffRequest {
    private String date;
    private String productId;
    private String brandId;
}
