package com.inditex.prices.rest;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceTariffControllerWebIT extends ControllerAbstractWebIT {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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
    @DisplayName("Find Price Tariff without sending date parameter returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenDateParameterNotSent_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", null);
        queryParams.put("productId", "35455");
        queryParams.put("brandId", "1");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Required String parameter 'date' is not present", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff without sending productId parameter returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenProductIdParameterNotSent_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", null);
        queryParams.put("brandId", "1");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Required String parameter 'productId' is not present", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff without sending brandId parameter returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenBrandIdParameterNotSent_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", "35455");
        queryParams.put("brandId", null);

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Required String parameter 'brandId' is not present", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending date parameter with wrong format returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenDateParameterSentWithWrongFormat_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", "ABC");
        queryParams.put("productId", "35455");
        queryParams.put("brandId", "1");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Parameter 'date' has wrong format", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending productId parameter with wrong format returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenProductIdParameterSentWithWrongFormat_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", "ABC");
        queryParams.put("brandId", "1");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Parameter 'productId' has wrong format", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending brandId parameter with wrong format returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenBrandIdParameterSentWithWrongFormat_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", "35455");
        queryParams.put("brandId", "ABC");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Parameter 'brandId' has wrong format", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending a non existing brand returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenBrandNotExist_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", "35455");
        queryParams.put("brandId", "10");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Brand does not exist", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending a non existing product returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenProductNotExist_thenReturnBadRequest() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", "35458");
        queryParams.put("brandId", "1");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Product does not exist", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format but no Price Tariff is found returns NOT_FOUND with the expected message")
    public void findPriceTariff_whenValidRequest_whenPriceTariffNotFound_thenReturnNotFound() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", Instant.now().toString());
        queryParams.put("productId", "35455");
        queryParams.put("brandId", "1");

        final ResponseEntity<String> response = restTemplate.getForEntity(constructUrl(port, queryParams), String.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Could not find a price tariff for the requested parameters", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and a Price Tariff is found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenOneApplicablePrice_thenReturnFoundPriceAndOk() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", productId.toString());
        queryParams.put("brandId", brandId.toString());

        final Price price = priceTariffHelper.constructAndInsertPrice(date, 1, productId, brandId, 11.11, 0);

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(price.getId(), priceTariffResponse.getPriceId());
        assertEquals(price.getProduct().getId(), priceTariffResponse.getProductId());
        assertEquals(price.getBrand().getId(), priceTariffResponse.getBrandId());
        assertEquals(price.getStartDate().toEpochMilli(), priceTariffResponse.getStartDate().toEpochMilli());
        assertEquals(price.getEndDate().toEpochMilli(), priceTariffResponse.getEndDate().toEpochMilli());
        assertEquals(price.getPrice(), priceTariffResponse.getPrice());
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and more than one applicable Price Tariffs with different priorities are found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenDifferentPriorities_thenReturnFoundPriceAndOk() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = Instant.now();
        final long productId = 35455L;
        final long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", Long.toString(productId));
        queryParams.put("brandId", Long.toString(brandId));

        final FindPriceTariffRequest request = new FindPriceTariffRequest(date.toString(), Long.toString(productId), Long.toString(brandId));

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(price2.getId(), priceTariffResponse.getPriceId());
        assertEquals(price2.getProduct().getId(), priceTariffResponse.getProductId());
        assertEquals(price2.getBrand().getId(), priceTariffResponse.getBrandId());
        assertEquals(price2.getStartDate().toEpochMilli(), priceTariffResponse.getStartDate().toEpochMilli());
        assertEquals(price2.getEndDate().toEpochMilli(), priceTariffResponse.getEndDate().toEpochMilli());
        assertEquals(price2.getPrice(), priceTariffResponse.getPrice());
    }


    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and more than one applicable Price Tariffs with same priorities are found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenSamePriorities_thenReturnFoundPriceAndOk() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        final Instant date = Instant.now();
        final long productId = 35455L;
        final long brandId = 1L;

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", date.toString());
        queryParams.put("productId", Long.toString(productId));
        queryParams.put("brandId", Long.toString(brandId));

        final Price price1 = priceTariffHelper.constructAndInsertPrice(date, 1, 35455L, 1L, 11.11, 1);
        final Price price2 = priceTariffHelper.constructAndInsertPrice(date, 2, 35455L, 1L, 22.22, 2);
        final Price price3 = priceTariffHelper.constructAndInsertPrice(date, 3, 35455L, 1L, 33.33, 0);
        final Price price4 = priceTariffHelper.constructAndInsertPrice(date, 4, 35455L, 1L, 44.44, 2);

        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.getForEntity(constructUrl(port, queryParams), FindPriceTariffResponse.class, queryParams);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        final FindPriceTariffResponse priceTariffResponse = response.getBody();
        assertNotNull(priceTariffResponse);
        assertEquals(price2.getId(), priceTariffResponse.getPriceId());
        assertEquals(price2.getProduct().getId(), priceTariffResponse.getProductId());
        assertEquals(price2.getBrand().getId(), priceTariffResponse.getBrandId());
        assertEquals(price2.getStartDate().toEpochMilli(), priceTariffResponse.getStartDate().toEpochMilli());
        assertEquals(price2.getEndDate().toEpochMilli(), priceTariffResponse.getEndDate().toEpochMilli());
        assertEquals(price2.getPrice(), priceTariffResponse.getPrice());
    }
}
