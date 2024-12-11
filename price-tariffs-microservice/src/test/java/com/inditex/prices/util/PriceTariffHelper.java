package com.inditex.prices.util;

import com.inditex.prices.model.Brand;
import com.inditex.prices.model.Currency;
import com.inditex.prices.model.Price;
import com.inditex.prices.model.Product;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.repository.ProductRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PriceTariffHelper {
    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public PriceTariffHelper(PriceRepository priceRepository, BrandRepository brandRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    public Price constructAndInsertPrice(Instant date, int days, Long productId, Long brandId, double price, int priority) {
        final Product product = productRepository.findById(productId).orElse(null);
        final Brand brand = brandRepository.findById(brandId).orElse(null);
        final Instant startDate = date.minus(days, ChronoUnit.DAYS);
        final Instant endDate = date.plus(days, ChronoUnit.DAYS);
        return constructAndInsertPrice(startDate, endDate, product, brand, price, priority);
    }

    private Price constructAndInsertPrice(Instant startDate, Instant endDate, Product product, Brand brand, double priceAmount, int priority) {
        final Price price = new Price();
        price.setPriority(priority);
        price.setPrice(priceAmount);
        price.setCurrency(Currency.EUR);
        price.setProduct(product);
        price.setBrand(brand);
        price.setStartDate(startDate);
        price.setEndDate(endDate);
        return priceRepository.save(price);
    }
}
