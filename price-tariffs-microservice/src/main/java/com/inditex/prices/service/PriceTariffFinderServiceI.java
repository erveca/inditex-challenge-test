package com.inditex.prices.service;

import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;

import java.time.Instant;

/**
 * The interface for the different Price Tariff Finder service implementations.
 */
public interface PriceTariffFinderServiceI {
    /**
     * Finds the existing highest priority Price Tariff based on the given parameters.
     *
     * @param date      the date
     * @param productId the product ID
     * @param brandId   the brand ID
     * @return the existing highest priority Price Tariff based on the given parameters
     * @throws PriceNotFoundException if no Price Tariff is found for the given parameters.
     */
    Price findPriceTariff(Instant date, Long productId, Long brandId) throws PriceNotFoundException;
}
