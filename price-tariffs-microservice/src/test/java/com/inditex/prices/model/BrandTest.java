package com.inditex.prices.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BrandTest {
    @Test
    @DisplayName("Can create a new Brand instance")
    public void canCreateBrand() {
        final Brand brand = new Brand();
    }

    @Test
    @DisplayName("Can set the Brand ID")
    public void canSetBrandId() {
        final Brand brand = new Brand();
        brand.setId(2L);

        assertEquals(2L, brand.getId());
    }

    @Test
    @DisplayName("Can set the Brand Name")
    public void canSetBrandName() {
        final Brand brand = new Brand();
        brand.setName("The Brand");

        assertEquals("The Brand", brand.getName());
    }

    @Test
    @DisplayName("Can set the Prices for a Brand")
    public void canSetBrandPrices() {
        final Price price1 = new Price();
        final Price price2 = new Price();

        final Brand brand = new Brand();
        brand.setPrices(List.of(price1, price2));

        assertNotNull(brand.getPrices());
        assertEquals(2, brand.getPrices().size());
        assertEquals(price1, brand.getPrices().get(0));
        assertEquals(price2, brand.getPrices().get(1));
    }
}
