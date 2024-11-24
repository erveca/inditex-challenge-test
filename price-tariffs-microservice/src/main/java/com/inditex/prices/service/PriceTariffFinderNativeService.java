package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.PriceRepository;

import java.time.Instant;

/**
 * Implementation of PriceTariffFinderServiceI using a Native query.
 */
public class PriceTariffFinderNativeService implements PriceTariffFinderServiceI {
    private PriceRepository priceRepository;

    public PriceTariffFinderNativeService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public PriceDto findPriceTariff(Instant date, Long productId, Long brandId) throws PriceNotFoundException {
        final Price price = priceRepository.findApplicablePriceForDateAndProductIdAndBrandId(date, productId, brandId)
                .orElseThrow(PriceNotFoundException::new);
        return PriceDto.fromEntity(price);
    }
}
