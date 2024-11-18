package com.inditex.prices.rest;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.MissingParameterException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.model.Price;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.repository.ProductRepository;
import com.inditex.prices.util.PriceTariffHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

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

    @BeforeAll
    public static void setup() {

    }

    @BeforeEach
    public void setupEach() {
        priceTariffHelper = new PriceTariffHelper(priceRepository, brandRepository, productRepository);
        priceRepository.deleteAll();
    }

    @Test
    @DisplayName("Find Price Tariff without sending date parameter throws expected exception")
    public void findPriceTariff_whenDateParameterNotSent_thenThrowsException() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        //request.setDate(null);
        request.setProductId("35455");
        request.setBrandId("1");

        assertThrows(
                MissingParameterException.class,
                () -> priceTariffController.findPriceTariff(request),
                "MissingParameterException was expected when date parameter is missing"
        );
    }

    @Test
    @DisplayName("Find Price Tariff without sending productId parameter throws expected exception")
    public void findPriceTariff_whenProductIdParameterNotSent_thenThrowsException() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        //request.setProductId(null);
        request.setBrandId("1");

        assertThrows(
                MissingParameterException.class,
                () -> priceTariffController.findPriceTariff(request),
                "MissingParameterException was expected when productId parameter is missing"
        );
    }

    @Test
    @DisplayName("Find Price Tariff without sending brandId parameter throws expected exception")
    public void findPriceTariff_whenBrandIdParameterNotSent_thenThrowsException() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("35455");
        //request.setBrandId(null);

        assertThrows(
                MissingParameterException.class,
                () -> priceTariffController.findPriceTariff(request),
                "MissingParameterException was expected when brandId parameter is missing"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending date parameter with wrong format throws expected exception")
    public void findPriceTariff_whenDateParameterSentWithWrongFormat_thenThrowsException() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate("ABC");
        request.setProductId("35455");
        request.setBrandId("1");

        assertThrows(
                InvalidDateException.class,
                () -> priceTariffController.findPriceTariff(request),
                "InvalidDateException was expected when date has wrong format"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending productId parameter with wrong format throws expected exception")
    public void findPriceTariff_whenProductIdParameterSentWithWrongFormat_thenThrowsException() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("ABC");
        request.setBrandId("1");

        assertThrows(
                InvalidProductException.class,
                () -> priceTariffController.findPriceTariff(request),
                "InvalidProductException was expected when productId parameter has wrong format"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending brandId parameter with wrong format throws expected exception")
    public void findPriceTariff_whenBrandIdParameterSentWithWrongFormat_thenThrowsException() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("35455");
        request.setBrandId("ABC");

        assertThrows(
                InvalidBrandException.class,
                () -> priceTariffController.findPriceTariff(request),
                "InvalidBrandException was expected when brandId has wrong format"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format but no Price Tariff is found throws expected exception")
    public void findPriceTariff_whenValidRequest_whenPriceTariffNotFound_thenThrowsException() {
        final Instant date = Instant.now();
        final long productId = 35455L;
        final long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(Long.toString(productId));
        request.setBrandId(Long.toString(brandId));

        assertThrows(
                PriceNotFoundException.class,
                () -> priceTariffController.findPriceTariff(request),
                "PriceNotFoundException was expected when no applicable prices exist"
        );
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and a Price Tariff is found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenOneApplicablePrice_thenReturnFoundPriceAndOk() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException, MissingParameterException {
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final Price price = priceTariffHelper.constructAndInsertPrice(date, 1, productId, brandId, 11.11, 0);

        final ResponseEntity response = priceTariffController.findPriceTariff(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody() instanceof FindPriceTariffResponse);

        final FindPriceTariffResponse priceTariffResponse = (FindPriceTariffResponse) response.getBody();
        assertEquals(price.getId(), priceTariffResponse.getPriceId());
        assertEquals(price.getProduct().getId(), priceTariffResponse.getProductId());
        assertEquals(price.getBrand().getId(), priceTariffResponse.getBrandId());
        assertEquals(price.getStartDate().toEpochMilli(), priceTariffResponse.getStartDate().toEpochMilli());
        assertEquals(price.getEndDate().toEpochMilli(), priceTariffResponse.getEndDate().toEpochMilli());
        assertEquals(price.getPrice(), priceTariffResponse.getPrice());
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and more than one applicable Price Tariffs with different priorities are found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenDifferentPriorities_thenReturnFoundPriceAndOk() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException, MissingParameterException {
        final Instant date = Instant.now();
        final long productId = 35455L;
        final long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(Long.toString(productId));
        request.setBrandId(Long.toString(brandId));

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);

        final ResponseEntity response = priceTariffController.findPriceTariff(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody() instanceof FindPriceTariffResponse);

        final FindPriceTariffResponse priceTariffResponse = (FindPriceTariffResponse) response.getBody();
        assertEquals(price2.getId(), priceTariffResponse.getPriceId());
        assertEquals(price2.getProduct().getId(), priceTariffResponse.getProductId());
        assertEquals(price2.getBrand().getId(), priceTariffResponse.getBrandId());
        assertEquals(price2.getStartDate().toEpochMilli(), priceTariffResponse.getStartDate().toEpochMilli());
        assertEquals(price2.getEndDate().toEpochMilli(), priceTariffResponse.getEndDate().toEpochMilli());
        assertEquals(price2.getPrice(), priceTariffResponse.getPrice());
    }


    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and more than one applicable Price Tariffs with same priorities are found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenSamePriorities_thenReturnFoundPriceAndOk() throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException, MissingParameterException {
        final Instant date = Instant.now();
        final long productId = 35455L;
        final long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(Long.toString(productId));
        request.setBrandId(Long.toString(brandId));

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);
        final Price price4 = priceTariffHelper.constructAndInsertPrice(date, 4, 35455L, 1L, 44.44, 2);

        final ResponseEntity response = priceTariffController.findPriceTariff(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody() instanceof FindPriceTariffResponse);

        final FindPriceTariffResponse priceTariffResponse = (FindPriceTariffResponse) response.getBody();
        assertEquals(price2.getId(), priceTariffResponse.getPriceId());
        assertEquals(price2.getProduct().getId(), priceTariffResponse.getProductId());
        assertEquals(price2.getBrand().getId(), priceTariffResponse.getBrandId());
        assertEquals(price2.getStartDate().toEpochMilli(), priceTariffResponse.getStartDate().toEpochMilli());
        assertEquals(price2.getEndDate().toEpochMilli(), priceTariffResponse.getEndDate().toEpochMilli());
        assertEquals(price2.getPrice(), priceTariffResponse.getPrice());
    }
}
