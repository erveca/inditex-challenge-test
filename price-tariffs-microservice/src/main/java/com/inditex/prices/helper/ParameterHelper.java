package com.inditex.prices.helper;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.MissingParameterException;

import java.time.Instant;

public final class ParameterHelper {
    public Instant parseDateParameter(final String dateParam) throws MissingParameterException, InvalidDateException {
        if (dateParam == null) {
            throw new MissingParameterException("Required parameter 'date' is missing");
        }

        try {
            return Instant.parse(dateParam);
        } catch (Exception exc) {
            throw new InvalidDateException("Parameter 'date' has wrong format");
        }
    }

    public Long parseProductIdParameter(final String productIdParam) throws MissingParameterException, InvalidProductException {
        if (productIdParam == null) {
            throw new MissingParameterException("Required parameter 'productId' is missing");
        }

        try {
            return Long.parseLong(productIdParam);
        } catch (Exception exc) {
            throw new InvalidProductException("Parameter 'productId' has wrong format");
        }
    }

    public Long parseBrandIdParameter(final String brandIdParam) throws MissingParameterException, InvalidBrandException {
        if (brandIdParam == null) {
            throw new MissingParameterException("Required parameter 'brandId' is missing");
        }

        try {
            return Long.parseLong(brandIdParam);
        } catch (Exception exc) {
            throw new InvalidBrandException("Parameter 'brandId' has wrong format");
        }
    }
}
