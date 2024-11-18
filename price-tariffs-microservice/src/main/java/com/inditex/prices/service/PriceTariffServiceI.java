package com.inditex.prices.service;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;

import java.time.Instant;

/**
 * Interface for the Price Tariff service implementation.
 */
public interface PriceTariffServiceI {
    /**
     * Finds the existing highest priority Price Tariff based on the given parameters.
     *
     * @param date      the date
     * @param productId the product ID
     * @param brandId   the brand ID
     * @return the existing highest priority Price Tariff based on the given parameters
     * @throws InvalidProductException if the product does not exist.
     * @throws InvalidBrandException   if the brand does not exist
     * @throws PriceNotFoundException  if no Price Tariff is found for the given parameters.
     */
    Price findPrice(Instant date, Long productId, Long brandId) throws InvalidProductException, InvalidBrandException, PriceNotFoundException;
}
