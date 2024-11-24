package com.inditex.prices.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.inditex.prices.util.PriceTariffHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceTariffControllerIT {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceTariffController priceTariffController;

    private PriceTariffHelper priceTariffHelper;

    @BeforeEach
    public void setupEach() {
        priceTariffHelper = new PriceTariffHelper(priceRepository, brandRepository, productRepository);
        priceRepository.deleteAll();
    }

    @Test
    @DisplayName("Find Price Tariff without sending date parameter throws expected exception")
    public void findPriceTariff_whenDateParameterNotSent_thenThrowsException() {
        assertThrows(
                InvalidDateException.class,
                () -> priceTariffController.findPriceTariff(null, "35455", "1"),
                "InvalidDateException was expected when date parameter is missing"
        );
    }

    @Test
    @DisplayName("Find Price Tariff without sending productId parameter throws expected exception")
    public void findPriceTariff_whenProductIdParameterNotSent_thenThrowsException() {
        assertThrows(
                InvalidProductException.class,
                () -> priceTariffController.findPriceTariff(Instant.now().toString(), null, "1"),
                "InvalidProductException was expected when productId parameter is missing"
        );
    }

    @Test
    @DisplayName("Find Price Tariff without sending brandId parameter throws expected exception")
    public void findPriceTariff_whenBrandIdParameterNotSent_thenThrowsException() {
        assertThrows(
                InvalidBrandException.class,
                () -> priceTariffController.findPriceTariff(Instant.now().toString(), "35455", null),
                "InvalidBrandException was expected when brandId parameter is missing"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending date parameter with wrong format throws expected exception")
    public void findPriceTariff_whenDateParameterSentWithWrongFormat_thenThrowsException() {
        assertThrows(
                InvalidDateException.class,
                () -> priceTariffController.findPriceTariff("ABC", "35455", "1"),
                "InvalidDateException was expected when date has wrong format"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending productId parameter with wrong format throws expected exception")
    public void findPriceTariff_whenProductIdParameterSentWithWrongFormat_thenThrowsException() {
        assertThrows(
                InvalidProductException.class,
                () -> priceTariffController.findPriceTariff(Instant.now().toString(), "ABC", "1"),
                "InvalidProductException was expected when productId parameter has wrong format"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending brandId parameter with wrong format throws expected exception")
    public void findPriceTariff_whenBrandIdParameterSentWithWrongFormat_thenThrowsException() {
        assertThrows(
                InvalidBrandException.class,
                () -> priceTariffController.findPriceTariff(Instant.now().toString(), "35455", "ABC"),
                "InvalidBrandException was expected when brandId has wrong format"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format but no Price Tariff is found throws expected exception")
    public void findPriceTariff_whenValidRequest_whenPriceTariffNotFound_thenThrowsException() {
        assertThrows(
                PriceNotFoundException.class,
                () -> priceTariffController.findPriceTariff(Instant.now().toString(), "35455", "1"),
                "PriceNotFoundException was expected when no applicable prices exist"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and a Price Tariff is found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenOneApplicablePrice_thenReturnFoundPriceAndOk() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException, JsonProcessingException {
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Price price = priceTariffHelper.constructAndInsertPrice(date, 1, productId, brandId, 11.11, 0);

        final ResponseEntity response = priceTariffController.findPriceTariff(date.toString(), productId.toString(), brandId.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody() instanceof FindPriceTariffResponse);

        final FindPriceTariffResponse priceTariffResponse = (FindPriceTariffResponse) response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(price.getId(), price.getProduct().getId(), price.getBrand().getId(), price.getStartDate(), price.getEndDate(), price.getPrice(), price.getCurrency());
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and more than one applicable Price Tariffs with different priorities are found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenDifferentPriorities_thenReturnFoundPriceAndOk() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final long productId = 35455L;
        final long brandId = 1L;

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);

        final ResponseEntity response = priceTariffController.findPriceTariff(date.toString(), Long.toString(productId), Long.toString(brandId));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody() instanceof FindPriceTariffResponse);

        final FindPriceTariffResponse priceTariffResponse = (FindPriceTariffResponse) response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(price2.getId(), price2.getProduct().getId(), price2.getBrand().getId(), price2.getStartDate(), price2.getEndDate(), price2.getPrice(), price2.getCurrency());
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }


    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and more than one applicable Price Tariffs with same priorities are found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenSamePriorities_thenReturnFoundPriceAndOk() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        final Instant date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        final long productId = 35455L;
        final long brandId = 1L;

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);
        final Price price4 = priceTariffHelper.constructAndInsertPrice(date, 4, 35455L, 1L, 44.44, 2);

        final ResponseEntity response = priceTariffController.findPriceTariff(date.toString(), Long.toString(productId), Long.toString(brandId));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody() instanceof FindPriceTariffResponse);

        final FindPriceTariffResponse priceTariffResponse = (FindPriceTariffResponse) response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(price2.getId(), price2.getProduct().getId(), price2.getBrand().getId(), price2.getStartDate(), price2.getEndDate(), price2.getPrice(), price2.getCurrency());
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }

    private FindPriceTariffResponse constructExpectedResponse(Long priceId, Long productId, Long brandId, Instant startDate, Instant endDate, Double price, Currency currency) {
        return FindPriceTariffResponse.builder()
                .priceId(priceId)
                .productId(productId)
                .brandId(brandId)
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .currency(currency)
                .build();
    }
}
