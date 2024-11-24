package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.helper.ProductHelper;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PriceTariffService extends PriceTariffAbstractService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceTariffFinderServiceI priceTariffFinderService;

    @Override
    protected boolean checkProductExists(Long productId) {
        return ProductHelper.getInstance().productExists(productRepository, productId);
    }

    @Override
    protected boolean checkBrandExists(Long brandId) {
        return brandRepository.existsById(brandId);
    }

    @Override
    protected PriceDto searchPriceTariff(Instant date, Long productId, Long brandId) throws PriceNotFoundException {
        return priceTariffFinderService.findPriceTariff(date, productId, brandId);
    }
}
