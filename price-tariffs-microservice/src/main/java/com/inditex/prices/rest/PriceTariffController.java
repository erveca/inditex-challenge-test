package com.inditex.prices.rest;

import com.inditex.prices.dto.FindPriceTariffRequest;
import com.inditex.prices.dto.FindPriceTariffResponse;
import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import com.inditex.prices.service.PriceTariffServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/price-tariffs")
public class PriceTariffController {
    @Autowired
    private PriceTariffServiceI priceTariffService;

    @GetMapping(path = "/find", produces = "application/json")
    public ResponseEntity findPriceTariff(@RequestParam("date") String dateParam,
                                          @RequestParam("productId") String productIdParam,
                                          @RequestParam("brandId") String brandIdParam)
            throws PriceNotFoundException, InvalidBrandException, InvalidProductException, InvalidDateException {
        log.debug("START Finding Price Tariff for given request. Date: {}, ProductId: {}, BrandId: {}", dateParam, productIdParam, brandIdParam);

        final FindPriceTariffRequest findPriceTariffRequest = new FindPriceTariffRequest(dateParam, productIdParam, brandIdParam);
        final FindPriceTariffResponse findPriceTariffResponse = priceTariffService.findPrice(findPriceTariffRequest);
        log.trace("Price Tariff Response: " + findPriceTariffResponse);

        log.debug("END Finding Price Tariff for given request.");
        return new ResponseEntity<>(findPriceTariffResponse, HttpStatus.OK);
    }
}
