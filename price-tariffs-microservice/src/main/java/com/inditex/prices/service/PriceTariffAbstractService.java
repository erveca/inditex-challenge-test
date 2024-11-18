package com.inditex.prices.service;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;

import java.time.Instant;

/**
 * Abstract class implementing the template for the findPrice method (Template Method design pattern).
 */
public abstract class PriceTariffAbstractService implements PriceTariffServiceI {
    /**
     * Searches for the applicable Price Tariff in the database.
     *
     * @param date      the date
     * @param productId the product ID
     * @param brandId   the brand ID
     * @return the existing highest priority Price Tariff based on the given parameters
     * @throws InvalidProductException if the given product does not exist
     * @throws InvalidBrandException   if the given brand does not exist
     * @throws PriceNotFoundException  if no Price Tariff is found for the given parameters.
     */
    @Override
    public Price findPrice(final Instant date, final Long productId, final Long brandId) throws InvalidProductException, InvalidBrandException, PriceNotFoundException {
        if (!checkProductExists(productId)) {
            throw new InvalidProductException("Product does not exist");
        }
        if (!checkBrandExists(brandId)) {
            throw new InvalidBrandException("Brand does not exist");
        }
        return searchPriceTariff(date, productId, brandId);
    }

    /**
     * Checks that the product exists.
     *
     * @param productId the product ID to check
     * @return true if the product exists. Otherwise, false.
     */
    protected abstract boolean checkProductExists(final Long productId);

    /**
     * Checks that the brand exists.
     *
     * @param brandId the brand ID to check.
     * @return true if the product exists. Otherwise, false.
     */
    protected abstract boolean checkBrandExists(final Long brandId);

    /**
     * Searches for the applicable Price Tariff in the database.
     *
     * @param date      the date
     * @param productId the product ID
     * @param brandId   the brand ID
     * @return the existing highest priority Price Tariff based on the given parameters
     * @throws PriceNotFoundException if no Price Tariff is found for the given parameters.
     */
    protected abstract Price searchPriceTariff(final Instant date, final Long productId, final Long brandId) throws PriceNotFoundException;
}
