package com.inditex.prices.service;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.dto.PriceDto;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Currency;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PriceTariffServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceTariffFinderServiceI priceTariffFinderService;

    @InjectMocks
    private PriceTariffService priceTariffService;

    @Test
    @DisplayName("Find Price for a non existing product throws the expected exception")
    public void findPrice_whenProductNotExist_thenThrowExpectedException() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        Mockito.when(productRepository.existsById(productId)).thenReturn(false);

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final InvalidProductException exception = assertThrows(
                InvalidProductException.class,
                () -> priceTariffService.findPrice(request),
                "InvalidProductException was expected when product does not exist"
        );

        // Then
        assertEquals("Product does not exist", exception.getMessage());

        Mockito.verify(productRepository).existsById(productId);
        Mockito.verifyNoInteractions(brandRepository);
        Mockito.verifyNoMoreInteractions(productRepository, brandRepository);
    }

    @Test
    @DisplayName("Find Price for a non existing brand throws the expected exception")
    public void findPrice_whenBrandNotExist_thenThrowExpectedException() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        Mockito.when(productRepository.existsById(productId)).thenReturn(true);
        Mockito.when(brandRepository.existsById(brandId)).thenReturn(false);

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final InvalidBrandException exception = assertThrows(
                InvalidBrandException.class,
                () -> priceTariffService.findPrice(request),
                "InvalidBrandException was expected when brand does not exist"
        );

        // Then
        assertEquals("Brand does not exist", exception.getMessage());

        Mockito.verify(productRepository).existsById(productId);
        Mockito.verify(brandRepository).existsById(brandId);
        Mockito.verifyNoMoreInteractions(productRepository, brandRepository);
    }

    @Test
    @DisplayName("Find Price for a non existing applicable price throws the expected exception")
    public void findPrice_whenProductAndBrandExist_whenNoApplicablePrices_thenThrowsExpectedException() throws PriceNotFoundException, InvalidDateException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        Mockito.when(productRepository.existsById(productId)).thenReturn(true);
        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(priceTariffFinderService.findPriceTariff(date, productId, brandId)).thenThrow(new PriceNotFoundException());

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        assertThrows(
                PriceNotFoundException.class,
                () -> priceTariffService.findPrice(request),
                "PriceNotFoundException was expected when no applicable prices exist"
        );

        // Then
        Mockito.verify(productRepository).existsById(productId);
        Mockito.verify(brandRepository).existsById(brandId);
        Mockito.verify(priceTariffFinderService).findPriceTariff(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(productRepository, brandRepository, priceTariffFinderService);
    }

    @Test
    @DisplayName("Find Price when one or more applicable prices exist then the price is returned")
    public void findPrice_whenProductAndBrandExist_whenOneOrMoreApplicablePrices_thenReturnPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        // Given
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;
        final Long priceId = 1L;

        final PriceDto price = constructPrice(priceId, date, 1, productId, brandId, 0);

        Mockito.when(productRepository.existsById(productId)).thenReturn(true);
        Mockito.when(brandRepository.existsById(brandId)).thenReturn(true);
        Mockito.when(priceTariffFinderService.findPriceTariff(date, productId, brandId)).thenReturn(price);

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final FindPriceTariffResponse response = priceTariffService.findPrice(request);

        // Then
        Assertions.assertEquals(price.getPriceId(), response.getPriceId());
        Assertions.assertEquals(price.getProductId(), response.getProductId());
        Assertions.assertEquals(price.getBrandId(), response.getBrandId());
        Assertions.assertEquals(price.getStartDate(), response.getStartDate());
        Assertions.assertEquals(price.getEndDate(), response.getEndDate());

        Mockito.verify(productRepository).existsById(productId);
        Mockito.verify(brandRepository).existsById(brandId);
        Mockito.verify(priceTariffFinderService).findPriceTariff(date, productId, brandId);
        Mockito.verifyNoMoreInteractions(productRepository, brandRepository, priceTariffFinderService);
    }

    private PriceDto constructPrice(Long priceId, Instant date, int days, Long productId, Long brandId, int priority) {
        final Instant startDate = date.minus(days, ChronoUnit.DAYS);
        final Instant endDate = date.plus(days, ChronoUnit.DAYS);
        return constructPrice(priceId, startDate, endDate, productId, brandId, priority);
    }

    private PriceDto constructPrice(Long priceId, Instant startDate, Instant endDate, Long productId, Long brandId, int priority) {
        return PriceDto.builder()
                .priceId(priceId)
                .priority(priority)
                .currency(Currency.EUR)
                .productId(productId)
                .brandId(brandId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
