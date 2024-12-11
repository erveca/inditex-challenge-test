package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.PriceRepository;

import java.time.Instant;
import java.util.Comparator;

/**
 * Implementation of PriceTariffFinderServiceI using a JPQL query.
 */
public class PriceTariffFinderJpqlService implements PriceTariffFinderServiceI {
    private PriceRepository priceRepository;

    public PriceTariffFinderJpqlService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public PriceDto findPriceTariff(Instant date, Long productId, Long brandId) throws PriceNotFoundException {
        final Price price = priceRepository.findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId)
                .stream().max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(PriceNotFoundException::new);
        return PriceDto.fromEntity(price);
    }
}
