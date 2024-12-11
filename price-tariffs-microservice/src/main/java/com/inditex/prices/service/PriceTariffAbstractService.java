package com.inditex.prices.service;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;

import java.time.Instant;

/**
 * Abstract class implementing the template for the findPrice method (Template Method design pattern).
 */
public abstract class PriceTariffAbstractService implements PriceTariffServiceI {
    /**
     * Searches for the applicable Price Tariff in the database.
     *
     * @param findPriceTariffRequest request object wrapping the date, product ID and brand ID for the search.
     * @return the existing highest priority Price Tariff based on the given parameters
     * @throws InvalidProductException if the given product does not exist
     * @throws InvalidBrandException   if the given brand does not exist
     * @throws PriceNotFoundException  if no Price Tariff is found for the given parameters.
     */
    @Override
    public FindPriceTariffResponse findPrice(final FindPriceTariffRequest findPriceTariffRequest) throws InvalidProductException, InvalidBrandException, PriceNotFoundException {
        if (!checkProductExists(findPriceTariffRequest.getProductId())) {
            throw new InvalidProductException("Product does not exist");
        }
        if (!checkBrandExists(findPriceTariffRequest.getBrandId())) {
            throw new InvalidBrandException("Brand does not exist");
        }
        final PriceDto price = searchPriceTariff(findPriceTariffRequest.getDate(), findPriceTariffRequest.getProductId(), findPriceTariffRequest.getBrandId());
        return FindPriceTariffResponse.fromPriceDto(price);
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
    protected abstract PriceDto searchPriceTariff(final Instant date, final Long productId, final Long brandId) throws PriceNotFoundException;
}
