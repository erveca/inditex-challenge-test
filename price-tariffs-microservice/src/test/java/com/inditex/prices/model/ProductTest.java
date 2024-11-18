package com.inditex.prices.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductTest {
    @Test
    @DisplayName("Can set the Product Name")
    public void canSetProductName() {
        final Product product = new Product();
        product.setName("The Product");

        assertEquals("The Product", product.getName());
    }

    @Test
    @DisplayName("Can set the Prices for a Product")
    public void canSetProductPrices() {
        final Price price1 = new Price();
        final Price price2 = new Price();
        final Price price3 = new Price();

        final Product product = new Product();
        product.setPrices(List.of(price1, price2, price3));

        assertNotNull(product.getPrices());
        assertEquals(3, product.getPrices().size());
        assertEquals(price1, product.getPrices().get(0));
        assertEquals(price2, product.getPrices().get(1));
        assertEquals(price3, product.getPrices().get(2));
    }
}
