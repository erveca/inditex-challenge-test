package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PriceTariffFinderNativeServiceTest extends PriceTariffFinderServiceAbstractTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceTariffFinderNativeService priceTariffFinderNativeService;


    @Test
    @DisplayName("Find Price for a non existing applicable price throws the expected exception")
    public void findPriceTariff_whenProductAndBrandExist_whenNoApplicablePrices_thenThrowsExpectedException() {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        Mockito.when(priceRepository.findApplicablePriceForDateAndProductIdAndBrandId(date, productId, brandId)).thenReturn(Optional.empty());

        // When
        assertThrows(
                PriceNotFoundException.class,
                () -> priceTariffFinderNativeService.findPriceTariff(date, productId, brandId),
                "PriceNotFoundException was expected when no applicable prices exist"
        );

        // Then
        Mockito.verify(priceRepository).findApplicablePriceForDateAndProductIdAndBrandId(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(priceRepository);
    }

    @Test
    @DisplayName("Find Price when one or more applicable price exists then the price is returned")
    public void findPriceTariff_whenProductAndBrandExist_whenOneOrMoreApplicablePrice_thenReturnPrice() throws PriceNotFoundException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;
        final Long priceId = 1L;

        final Price price = constructPrice(priceId, date, 1, productId, brandId, 0);

        Mockito.when(priceRepository.findApplicablePriceForDateAndProductIdAndBrandId(date, productId, brandId)).thenReturn(Optional.of(price));

        // When
        final PriceDto priceResult = priceTariffFinderNativeService.findPriceTariff(date, productId, brandId);

        // Then
        Assertions.assertEquals(price.getId(), priceResult.getPriceId());
        Assertions.assertEquals(price.getPriority(), priceResult.getPriority());
        Assertions.assertEquals(price.getProduct().getId(), priceResult.getProductId());
        Assertions.assertEquals(price.getBrand().getId(), priceResult.getBrandId());
        Assertions.assertEquals(price.getStartDate(), priceResult.getStartDate());
        Assertions.assertEquals(price.getEndDate(), priceResult.getEndDate());

        Mockito.verify(priceRepository).findApplicablePriceForDateAndProductIdAndBrandId(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(priceRepository);
    }
}
