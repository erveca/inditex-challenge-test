package com.inditex.prices.service;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;

/**
 * Interface for the Price Tariff service implementation.
 */
public interface PriceTariffServiceI {
    /**
     * Finds the existing highest priority Price Tariff based on the given parameter.
     *
     * @param findPriceTariffRequest request object wrapping the date, product ID and brand ID for the search.
     * @return the existing highest priority Price Tariff based on the given parameters
     * @throws InvalidProductException if the product does not exist.
     * @throws InvalidBrandException   if the brand does not exist
     * @throws PriceNotFoundException  if no Price Tariff is found for the given parameters.
     */
    FindPriceTariffResponse findPrice(FindPriceTariffRequest findPriceTariffRequest) throws InvalidProductException, InvalidBrandException, PriceNotFoundException;
}
