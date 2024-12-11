package com.inditex.prices.dto;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class FindPriceTariffRequest {
    private Instant date;
    private Long productId;
    private Long brandId;

    public FindPriceTariffRequest(final String date, final String productId, final String brandId) throws InvalidDateException, InvalidProductException, InvalidBrandException {
        this.date = parseDateParameter(date);
        this.productId = parseProductIdParameter(productId);
        this.brandId = parseBrandIdParameter(brandId);
    }

    private Instant parseDateParameter(final String dateParam) throws InvalidDateException {
        if (dateParam == null) {
            throw new InvalidDateException("Required parameter 'date' is null");
        }

        try {
            return Instant.parse(dateParam);
        } catch (Exception exc) {
            throw new InvalidDateException("Parameter 'date' has wrong format");
        }
    }

    private Long parseProductIdParameter(final String productIdParam) throws InvalidProductException {
        if (productIdParam == null) {
            throw new InvalidProductException("Required parameter 'productId' is null");
        }

        try {
            return Long.parseLong(productIdParam);
        } catch (Exception exc) {
            throw new InvalidProductException("Parameter 'productId' has wrong format");
        }
    }

    private Long parseBrandIdParameter(final String brandIdParam) throws InvalidBrandException {
        if (brandIdParam == null) {
            throw new InvalidBrandException("Required parameter 'brandId' is null");
        }

        try {
            return Long.parseLong(brandIdParam);
        } catch (Exception exc) {
            throw new InvalidBrandException("Parameter 'brandId' has wrong format");
        }
    }
}
