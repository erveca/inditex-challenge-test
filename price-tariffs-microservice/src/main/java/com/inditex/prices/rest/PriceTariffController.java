package com.inditex.prices.rest;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.MissingParameterException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.helper.ParameterHelper;
import com.inditex.prices.model.Price;
import com.inditex.prices.service.PriceTariffServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/price-tariffs")
public class PriceTariffController {
    @Autowired
    private PriceTariffServiceI priceTariffService;

    @GetMapping(path = "/find", consumes = "application/json", produces = "application/json")
    public ResponseEntity findPriceTariff(@RequestBody FindPriceTariffRequest request) throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException, MissingParameterException {
        log.debug("START Finding Price Tariff for given request.");
        final String dateParam = request.getDate();
        final String productIdParam = request.getProductId();
        final String brandIdParam = request.getBrandId();

        log.debug("Date param: " + dateParam);
        log.debug("ProductId param: " + productIdParam);
        log.debug("BrandId param: " + brandIdParam);

        final ParameterHelper parameterHelper = new ParameterHelper();
        final Instant date = parameterHelper.parseDateParameter(dateParam);
        final long productId = parameterHelper.parseProductIdParameter(productIdParam);
        final long brandId = parameterHelper.parseBrandIdParameter(brandIdParam);

        final Price price = priceTariffService.findPrice(date, productId, brandId);
        log.trace("Price: " + price);

        final FindPriceTariffResponse findPriceTariffResponse = FindPriceTariffResponse.builder()
                .productId(price.getProduct().getId())
                .brandId(price.getBrand().getId())
                .priceId(price.getId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
        log.trace("Price Tariff Response: " + findPriceTariffResponse);

        log.debug("END Finding Price Tariff for given request.");
        return new ResponseEntity<>(findPriceTariffResponse, HttpStatus.OK);
    }
}
