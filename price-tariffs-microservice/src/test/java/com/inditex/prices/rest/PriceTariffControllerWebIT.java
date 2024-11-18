package com.inditex.prices.rest;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceTariffControllerWebIT extends ControllerAbstractWebIT {
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

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

    static {
        HTTP_HEADERS.set("Content-Type", "application/json");
    }

    @BeforeAll
    public static void setup() {
    }

    @BeforeEach
    public void setupEach() {
        restTemplate.getRestTemplate().setRequestFactory(new CustomHttpComponentsClientHttpRequestFactory());
        priceTariffHelper = new PriceTariffHelper(priceRepository, brandRepository, productRepository);
        priceRepository.deleteAll();
    }

    @Test
    @DisplayName("Find Price Tariff without sending date parameter returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenDateParameterNotSent_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(null);
        request.setProductId("35455");
        request.setBrandId("1");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Required parameter 'date' is missing", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff without sending productId parameter returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenProductIdParameterNotSent_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        //request.setProductId(null);
        request.setBrandId("1");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Required parameter 'productId' is missing", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff without sending brandId parameter returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenBrandIdParameterNotSent_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("35455");
        //request.setBrandId(null);

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Required parameter 'brandId' is missing", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending date parameter with wrong format returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenDateParameterSentWithWrongFormat_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate("ABC");
        request.setProductId("35455");
        request.setBrandId("1");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Parameter 'date' has wrong format", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending productId parameter with wrong format returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenProductIdParameterSentWithWrongFormat_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("ABC");
        request.setBrandId("1");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Parameter 'productId' has wrong format", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending brandId parameter with wrong format returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenBrandIdParameterSentWithWrongFormat_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("35455");
        request.setBrandId("ABC");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Parameter 'brandId' has wrong format", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending a non existing brand returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenBrandNotExist_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("35455");
        request.setBrandId("10");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Brand does not exist", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending a non existing product returns BAD_REQUEST with the expected message")
    public void findPriceTariff_whenProductNotExist_thenReturnBadRequest() {
        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(Instant.now().toString());
        request.setProductId("35458");
        request.setBrandId("1");

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Product does not exist", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format but no Price Tariff is found returns NOT_FOUND with the expected message")
    public void findPriceTariff_whenValidRequest_whenPriceTariffNotFound_thenReturnNotFound() {
        final Instant date = Instant.now();
        final long productId = 35455L;
        final long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(Long.toString(productId));
        request.setBrandId(Long.toString(brandId));

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("Could not find a price tariff for the requested parameters", response.getBody());
    }

    @Test
    @DisplayName("Find Price Tariff sending all parameters with valid format and a Price Tariff is found returns OK with the expected Price Tariff")
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenOneApplicablePrice_thenReturnFoundPriceAndOk() {
        final Instant date = Instant.now();
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffRequest request = new FindPriceTariffRequest();
        request.setDate(date.toString());
        request.setProductId(productId.toString());
        request.setBrandId(brandId.toString());

        final Price price = priceTariffHelper.constructAndInsertPrice(date, 1, productId, brandId, 11.11, 0);

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

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
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenDifferentPriorities_thenReturnFoundPriceAndOk() {
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

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

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
    public void findPriceTariff_whenValidRequest_whenPriceTariffFound_whenSeveralApplicablePrices_whenSamePriorities_thenReturnFoundPriceAndOk() {
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

        final HttpEntity<FindPriceTariffRequest> httpRequestEntity = new HttpEntity<>(request, HTTP_HEADERS);
        final ResponseEntity<FindPriceTariffResponse> response = restTemplate.exchange(constructUrl(port), HttpMethod.GET, httpRequestEntity, FindPriceTariffResponse.class);

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
