package com.inditex.prices.rest.it.web;

import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.model.Currency;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ControllerAbstractWebIT {
    private static final String ENDPOINT_FORMAT = "http://localhost:%s/price-tariffs/find?";

    protected String constructUrl(int port, final Map<String, String> queryParams) {
        final StringBuilder sb = new StringBuilder(ENDPOINT_FORMAT);
        sb.append(queryParams.keySet().stream()
                .filter(param -> queryParams.get(param) != null)
                .map(param -> "%s={%s}".formatted(param, param))
                .collect(Collectors.joining("&")));
        return sb.toString().formatted(port);
    }

    protected FindPriceTariffResponse constructExpectedResponse(Long priceId, Long productId, Long brandId, String startDate, String endDate, Double price, Currency currency) {
        return FindPriceTariffResponse.builder()
                .priceId(priceId)
                .productId(productId)
                .brandId(brandId)
                .startDate(Instant.parse(startDate))
                .endDate(Instant.parse(endDate))
                .price(price)
                .currency(currency)
                .build();
    }
}
