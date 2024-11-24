package com.inditex.prices.service;

import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidProductException;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PriceTariffFinderJpqlServiceTest extends PriceTariffFinderServiceAbstractTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceTariffFinderJpqlService priceTariffFinderJpqlService;

    @Test
    @DisplayName("Find Price for a non existing applicable price throws the expected exception")
    public void findPriceTariff_whenProductAndBrandExist_whenNoApplicablePrices_thenThrowsExpectedException() throws PriceNotFoundException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        Mockito.when(priceRepository.findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId)).thenReturn(Collections.emptyList());

        // When
        assertThrows(
                PriceNotFoundException.class,
                () -> priceTariffFinderJpqlService.findPriceTariff(date, productId, brandId),
                "PriceNotFoundException was expected when no applicable prices exist"
        );

        // Then
        Mockito.verify(priceRepository).findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(priceRepository);
    }

    @Test
    @DisplayName("Find Price when one applicable price exists then the price is returned")
    public void findPriceTariff_whenProductAndBrandExist_whenOneApplicablePrice_thenReturnPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;
        final Long priceId = 1L;

        final Price price = constructPrice(priceId, date, 1, productId, brandId, 0);

        Mockito.when(priceRepository.findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId)).thenReturn(List.of(price));

        // When
        final PriceDto priceResult = priceTariffFinderJpqlService.findPriceTariff(date, productId, brandId);

        // Then
        Assertions.assertEquals(price.getId(), priceResult.getPriceId());
        Assertions.assertEquals(price.getPriority(), priceResult.getPriority());
        Assertions.assertEquals(price.getProduct().getId(), priceResult.getProductId());
        Assertions.assertEquals(price.getBrand().getId(), priceResult.getBrandId());
        Assertions.assertEquals(price.getStartDate(), priceResult.getStartDate());
        Assertions.assertEquals(price.getEndDate(), priceResult.getEndDate());

        Mockito.verify(priceRepository).findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(priceRepository);
    }

    @Test
    @DisplayName("Find Price when more than one applicable price exist with different priorities then the expected price is returned")
    public void findPriceTariff_whenProductAndBrandExist_whenSeveralApplicablePrice_whenDifferentPriorities_thenReturnExpectedPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Price price1 = constructPrice(1L, date, 1, 35455L, 1L, 1);
        final Price price2 = constructPrice(2L, date, 2, 35455L, 1L, 2);
        final Price price3 = constructPrice(3L, date, 3, 35455L, 1L, 0);

        Mockito.when(priceRepository.findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId)).thenReturn(List.of(price1, price2, price3));

        // When
        final PriceDto priceResult = priceTariffFinderJpqlService.findPriceTariff(date, productId, brandId);

        // Then
        Assertions.assertEquals(price2.getId(), priceResult.getPriceId());
        Assertions.assertEquals(price2.getPriority(), priceResult.getPriority());
        Assertions.assertEquals(price2.getProduct().getId(), priceResult.getProductId());
        Assertions.assertEquals(price2.getBrand().getId(), priceResult.getBrandId());
        Assertions.assertEquals(price2.getStartDate(), priceResult.getStartDate());
        Assertions.assertEquals(price2.getEndDate(), priceResult.getEndDate());

        Mockito.verify(priceRepository).findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(priceRepository);
    }

    @Test
    @DisplayName("Find Price when more than one applicable price exist with same priorities then the expected price is returned")
    public void findPriceTariff_whenProductAndBrandExist_whenSeveralApplicablePrice_whenSamePriorities_thenReturnExpectedPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Price price1 = constructPrice(1L, date, 1, 35455L, 1L, 1);
        final Price price2 = constructPrice(2L, date, 2, 35455L, 1L, 2);
        final Price price3 = constructPrice(3L, date, 3, 35455L, 1L, 0);
        final Price price4 = constructPrice(4L, date, 4, 35455L, 1L, 2);

        Mockito.when(priceRepository.findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId)).thenReturn(List.of(price1, price2, price3, price4));

        // When
        final PriceDto priceResult = priceTariffFinderJpqlService.findPriceTariff(date, productId, brandId);

        // Then
        Assertions.assertEquals(price2.getId(), priceResult.getPriceId());
        Assertions.assertEquals(price2.getPriority(), priceResult.getPriority());
        Assertions.assertEquals(price2.getProduct().getId(), priceResult.getProductId());
        Assertions.assertEquals(price2.getBrand().getId(), priceResult.getBrandId());
        Assertions.assertEquals(price2.getStartDate(), priceResult.getStartDate());
        Assertions.assertEquals(price2.getEndDate(), priceResult.getEndDate());

        Mockito.verify(priceRepository).findApplicablePricesForDateAndProductIdAndBrandId(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(priceRepository);
    }
}
