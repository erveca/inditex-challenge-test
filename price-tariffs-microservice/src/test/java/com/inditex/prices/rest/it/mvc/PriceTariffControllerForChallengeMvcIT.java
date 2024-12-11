package com.inditex.prices.rest.it.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.model.Currency;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class PriceTariffControllerForChallengeMvcIT {
    protected static final String ENDPOINT_FORMAT = "/price-tariffs/find?date={date}&productId={productId}&brandId={brandId}";

    protected FindPriceTariffResponse constructExpectedResponse(Long priceId, Long productId, Long brandId, String startDate, String endDate, Double price, Currency currency) {
        return FindPriceTariffResponse.builder()
                .priceId(priceId)
                .productId(productId)
                .brandId(brandId)
                .startDate(Instant.parse(startDate))
                .endDate(Instant.parse(endDate))
                .price(price)
                .currency(currency)
                .build();
    }

    protected void findPriceTariff_test1() throws Exception {
        final Instant date = LocalDateTime.of(2020, 6, 14, 10, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(1L, productId, brandId, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50, Currency.EUR);
        final String expectedPriceTariffResponseStr = this.getObjectMapper().writeValueAsString(expectedPriceTariffResponse);

        getMockMvc().perform(MockMvcRequestBuilders.get(ENDPOINT_FORMAT, date.toString(), productId.toString(), brandId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedPriceTariffResponseStr));
    }

    protected void findPriceTariff_test2() throws Exception {
        final Instant date = LocalDateTime.of(2020, 6, 14, 16, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(2L, productId, brandId, "2020-06-14T15:00:00Z", "2020-06-14T18:30:00Z", 25.45, Currency.EUR);
        final String expectedPriceTariffResponseStr = this.getObjectMapper().writeValueAsString(expectedPriceTariffResponse);

        getMockMvc().perform(MockMvcRequestBuilders.get(ENDPOINT_FORMAT, date.toString(), productId.toString(), brandId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedPriceTariffResponseStr));
    }

    protected void findPriceTariff_test3() throws Exception {
        final Instant date = LocalDateTime.of(2020, 6, 14, 21, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(1L, productId, brandId, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50, Currency.EUR);
        final String expectedPriceTariffResponseStr = this.getObjectMapper().writeValueAsString(expectedPriceTariffResponse);

        getMockMvc().perform(MockMvcRequestBuilders.get(ENDPOINT_FORMAT, date.toString(), productId.toString(), brandId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedPriceTariffResponseStr));
    }

    protected void findPriceTariff_test4() throws Exception {
        final Instant date = LocalDateTime.of(2020, 6, 15, 10, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(3L, productId, brandId, "2020-06-15T00:00:00Z", "2020-06-15T11:00:00Z", 30.50, Currency.EUR);
        final String expectedPriceTariffResponseStr = this.getObjectMapper().writeValueAsString(expectedPriceTariffResponse);

        getMockMvc().perform(MockMvcRequestBuilders.get(ENDPOINT_FORMAT, date.toString(), productId.toString(), brandId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedPriceTariffResponseStr));
    }

    protected void findPriceTariff_test5() throws Exception {
        final Instant date = LocalDateTime.of(2020, 6, 16, 21, 0, 0).toInstant(ZoneOffset.UTC);
        final Long productId = 35455L;
        final Long brandId = 1L;

        final FindPriceTariffResponse expectedPriceTariffResponse = constructExpectedResponse(4L, productId, brandId, "2020-06-15T16:00:00Z", "2020-12-31T23:59:59Z", 38.95, Currency.EUR);
        final String expectedPriceTariffResponseStr = this.getObjectMapper().writeValueAsString(expectedPriceTariffResponse);

        getMockMvc().perform(MockMvcRequestBuilders.get(ENDPOINT_FORMAT, date.toString(), productId.toString(), brandId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedPriceTariffResponseStr));
    }

    protected abstract MockMvc getMockMvc();

    protected abstract ObjectMapper getObjectMapper();
}
