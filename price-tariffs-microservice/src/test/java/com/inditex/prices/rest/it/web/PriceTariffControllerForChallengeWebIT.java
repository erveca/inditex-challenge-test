package com.inditex.prices.rest.it.web;

import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class PriceTariffControllerForChallengeWebIT extends ControllerAbstractWebIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    protected void findPriceTariff_test1() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = LocalDateTime.of(2020, 6, 14, 10, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", productId.toString());
        queryParams.put("brandId", brandId.toString());

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(1L, productId, brandId, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50, Currency.EUR);
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }

    protected void findPriceTariff_test2() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = LocalDateTime.of(2020, 6, 14, 16, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", productId.toString());
        queryParams.put("brandId", brandId.toString());

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(2L, productId, brandId, "2020-06-14T15:00:00Z", "2020-06-14T18:30:00Z", 25.45, Currency.EUR);
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }

    protected void findPriceTariff_test3() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = LocalDateTime.of(2020, 6, 14, 21, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", productId.toString());
        queryParams.put("brandId", brandId.toString());

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(1L, productId, brandId, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50, Currency.EUR);
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }

    protected void findPriceTariff_test4() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = LocalDateTime.of(2020, 6, 15, 10, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", productId.toString());
        queryParams.put("brandId", brandId.toString());

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(3L, productId, brandId, "2020-06-15T00:00:00Z", "2020-06-15T11:00:00Z", 30.50, Currency.EUR);
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }


    protected void findPriceTariff_test5() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = LocalDateTime.of(2020, 6, 16, 21, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", productId.toString());
        queryParams.put("brandId", brandId.toString());

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(4L, productId, brandId, "2020-06-15T16:00:00Z", "2020-12-31T23:59:59Z", 38.95, Currency.EUR);
        assertEquals(expectedPriceTariffResponse, priceTariffResponse);
    }
}
