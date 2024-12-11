package com.inditex.prices.config;

import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.service.PriceTariffFinderJpqlService;
import com.inditex.prices.service.PriceTariffFinderNativeService;
import com.inditex.prices.service.PriceTariffFinderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Autowired
    private PriceRepository priceRepository;

    @Bean
    @ConditionalOnProperty(name = "priceTariffFinderService", havingValue = "jpql")
    public PriceTariffFinderServiceI getPriceTariffFinderJpqlService() {
        return new PriceTariffFinderJpqlService(priceRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "priceTariffFinderService", havingValue = "native")
    public PriceTariffFinderServiceI getPriceTariffFinderNativeService() {
        return new PriceTariffFinderNativeService(priceRepository);
    }
}
