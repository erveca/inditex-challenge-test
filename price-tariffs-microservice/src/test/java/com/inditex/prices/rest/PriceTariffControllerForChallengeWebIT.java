package com.inditex.prices.rest;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class PriceTariffControllerForChallengeWebIT extends ControllerAbstractWebIT {
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static {
        HTTP_HEADERS.set("Content-Type", "application/json");
    }

    @BeforeAll
    public static void setup() {
    }

    @BeforeEach
    public void setupEach() {
        restTemplate.getRestTemplate().setRequestFactory(new CustomHttpComponentsClientHttpRequestFactory());
    }

    protected void findPriceTariff_test1() {
        final Instant date = LocalDateTime.of(2020, 6, 14, 10, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(1L, priceTariffResponse.getPriceId());
        assertEquals(productId, priceTariffResponse.getProductId());
        assertEquals(brandId, priceTariffResponse.getBrandId());
        assertEquals("2020-06-14T00:00:00Z", priceTariffResponse.getStartDate().toString());
        assertEquals("2020-12-31T23:59:59Z", priceTariffResponse.getEndDate().toString());
        assertEquals(35.50, priceTariffResponse.getPrice());
    }

    protected void findPriceTariff_test2() {
        final Instant date = LocalDateTime.of(2020, 6, 14, 16, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(2L, priceTariffResponse.getPriceId());
        assertEquals(productId, priceTariffResponse.getProductId());
        assertEquals(brandId, priceTariffResponse.getBrandId());
        assertEquals("2020-06-14T15:00:00Z", priceTariffResponse.getStartDate().toString());
        assertEquals("2020-06-14T18:30:00Z", priceTariffResponse.getEndDate().toString());
        assertEquals(25.45, priceTariffResponse.getPrice());
    }

    protected void findPriceTariff_test3() {
        final Instant date = LocalDateTime.of(2020, 6, 14, 21, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(1L, priceTariffResponse.getPriceId());
        assertEquals(productId, priceTariffResponse.getProductId());
        assertEquals(brandId, priceTariffResponse.getBrandId());
        assertEquals("2020-06-14T00:00:00Z", priceTariffResponse.getStartDate().toString());
        assertEquals("2020-12-31T23:59:59Z", priceTariffResponse.getEndDate().toString());
        assertEquals(35.50, priceTariffResponse.getPrice());
    }

    protected void findPriceTariff_test4() {
        final Instant date = LocalDateTime.of(2020, 6, 15, 10, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(3L, priceTariffResponse.getPriceId());
        assertEquals(productId, priceTariffResponse.getProductId());
        assertEquals(brandId, priceTariffResponse.getBrandId());
        assertEquals("2020-06-15T00:00:00Z", priceTariffResponse.getStartDate().toString());
        assertEquals("2020-06-15T11:00:00Z", priceTariffResponse.getEndDate().toString());
        assertEquals(30.50, priceTariffResponse.getPrice());
    }


    protected void findPriceTariff_test5() {
        final Instant date = LocalDateTime.of(2020, 6, 16, 21, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(4L, priceTariffResponse.getPriceId());
        assertEquals(productId, priceTariffResponse.getProductId());
        assertEquals(brandId, priceTariffResponse.getBrandId());
        assertEquals("2020-06-15T16:00:00Z", priceTariffResponse.getStartDate().toString());
        assertEquals("2020-12-31T23:59:59Z", priceTariffResponse.getEndDate().toString());
        assertEquals(38.95, priceTariffResponse.getPrice());
    }
}
