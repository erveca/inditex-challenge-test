package com.inditex.prices.service.it;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Currency;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.repository.ProductRepository;
import com.inditex.prices.service.PriceTariffService;
import com.inditex.prices.util.PriceTariffHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceTariffServiceIT {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceTariffService priceTariffService;

    private PriceTariffHelper priceTariffHelper;

    @BeforeEach
    public void setupEach() {
        priceTariffHelper = new PriceTariffHelper(priceRepository, brandRepository, productRepository);
        priceRepository.deleteAll();
    }

    @Test
    @DisplayName("Find Price for a non existing product throws the expected exception")
    public void findPrice_whenProductNotExist_thenThrowExpectedException() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35458L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final InvalidProductException exception = assertThrows(
                InvalidProductException.class,
                () -> priceTariffService.findPrice(request),
                "InvalidProductException was expected when product does not exist"
        );

        // Then
        assertEquals("Product does not exist", exception.getMessage());
    }

    @Test
    @DisplayName("Find Price for a non existing brand throws the expected exception")
    public void findPrice_whenBrandNotExist_thenThrowExpectedException() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35455L;
        final Long brandId = 10L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final InvalidBrandException exception = assertThrows(
                InvalidBrandException.class,
                () -> priceTariffService.findPrice(request),
                "InvalidBrandException was expected when brand does not exist"
        );

        // Then
        assertEquals("Brand does not exist", exception.getMessage());
    }

    @Test
    @DisplayName("Find Price for a non existing applicable price throws the expected exception")
    public void findPrice_whenProductAndBrandExist_whenNoApplicablePrices_thenThrowsExpectedException() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        // Given
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        assertThrows(
                PriceNotFoundException.class,
                () -> priceTariffService.findPrice(request),
                "PriceNotFoundException was expected when no applicable prices exist"
        );

        // Then
    }

    @Test
    @DisplayName("Find Price when one applicable price exists then the price is returned")
    public void findPrice_whenProductAndBrandExist_whenOneApplicablePrice_thenReturnPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        // Given
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Price price = priceTariffHelper.constructAndInsertPrice(date, 1, productId, brandId, 11.00, 0);

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final FindPriceTariffResponse response = priceTariffService.findPrice(request);

        // Then
        assertNotNull(response);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(price.getId(), price.getProduct().getId(), price.getBrand().getId(), price.getStartDate().toEpochMilli(), price.getEndDate().toEpochMilli(), price.getPrice(), price.getCurrency());
        assertEquals(expectedPriceTariffResponse, response);
    }


    @Test
    @DisplayName("Find Price when more than one applicable price exist with different priorities then the expected price is returned")
    public void findPrice_whenProductAndBrandExist_whenSeveralApplicablePrice_whenDifferentPriorities_thenReturnExpectedPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        // Given
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final FindPriceTariffResponse response = priceTariffService.findPrice(request);

        // Then
        assertNotNull(response);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(price2.getId(), price2.getProduct().getId(), price2.getBrand().getId(), price2.getStartDate().toEpochMilli(), price2.getEndDate().toEpochMilli(), price2.getPrice(), price2.getCurrency());
        assertEquals(expectedPriceTariffResponse, response);
    }

    @Test
    @DisplayName("Find Price when more than one applicable price exist with same priorities then the expected price is returned")
    public void findPrice_whenProductAndBrandExist_whenSeveralApplicablePrice_whenSamePriorities_thenReturnExpectedPrice() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        // Given
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);
        final Price price4 = priceTariffHelper.constructAndInsertPrice(date, 4, 35455L, 1L, 44.44, 2);

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), productId.toString(), brandId.toString());

        // When
        final FindPriceTariffResponse response = priceTariffService.findPrice(request);

        // Then
        assertNotNull(response);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(price2.getId(), price2.getProduct().getId(), price2.getBrand().getId(), price2.getStartDate().toEpochMilli(), price2.getEndDate().toEpochMilli(), price2.getPrice(), price2.getCurrency());
        assertEquals(expectedPriceTariffResponse, response);
    }

    private FindPriceTariffResponse constructExpectedResponse(Long priceId, Long productId, Long brandId, Long startDate, Long endDate, Double price, Currency currency) {
        return FindPriceTariffResponse.builder()
                .priceId(priceId)
                .productId(productId)
                .brandId(brandId)
                .startDate(Instant.ofEpochMilli(startDate))
                .endDate(Instant.ofEpochMilli(endDate))
                .price(price)
                .currency(currency)
                .build();
    }
}
