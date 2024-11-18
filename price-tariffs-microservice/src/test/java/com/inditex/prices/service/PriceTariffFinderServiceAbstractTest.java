package com.inditex.prices.service;

import com.inditex.prices.model.Brand;
import com.inditex.prices.model.Currency;
import com.inditex.prices.model.Price;
import com.inditex.prices.model.Product;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PriceTariffFinderServiceAbstractTest {
    protected Price constructPrice(Long priceId, Instant date, int days, Long productId, Long brandId, int priority) {
        final Product product = constructProduct(productId);
        final Brand brand = constructBrand(brandId);
        final Instant startDate = date.minus(days, ChronoUnit.DAYS);
        final Instant endDate = date.plus(days, ChronoUnit.DAYS);
        return constructPrice(priceId, startDate, endDate, product, brand, priority);
    }

    private Product constructProduct(Long productId) {
        final Product product = new Product();
        product.setId(productId);
        return product;
    }

    private Brand constructBrand(Long brandId) {
        final Brand brand = new Brand();
        brand.setId(brandId);
        return brand;
    }

    private Price constructPrice(Long priceId, Instant startDate, Instant endDate, Product product, Brand brand, int priority) {
        final Price price = new Price();
        price.setId(priceId);
        price.setPriority(priority);
        price.setCurrency(Currency.EUR);
        price.setProduct(product);
        price.setBrand(brand);
        price.setStartDate(startDate);
        price.setEndDate(endDate);
        return price;
    }
}
