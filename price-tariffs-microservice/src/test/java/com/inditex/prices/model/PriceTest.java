package com.inditex.prices.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceTest {
    @Test
    @DisplayName("Can set the Price for a Price Tariff")
    public void canSetPrice() {
        final Price price = new Price();
        price.setPrice(12.34);

        assertEquals(12.34, price.getPrice());
    }

    @Test
    @DisplayName("Can set the Price Currency for a Price Tariff")
    public void canSetCurrency() {
        final Price price = new Price();
        price.setCurrency(Currency.EUR);

        assertEquals(Currency.EUR, price.getCurrency());
    }
}
