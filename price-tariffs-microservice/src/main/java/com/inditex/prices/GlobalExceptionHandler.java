package com.inditex.prices;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MissingServletRequestParameterException.class, TypeMismatchException.class, PriceNotFoundException.class, InvalidDateException.class, InvalidBrandException.class, InvalidProductException.class})
    public final ResponseEntity<String> handleException(Exception exc, WebRequest request) {
        log.debug("Handling exception of class: " + exc.getClass().getCanonicalName());
        final HttpHeaders headers = new HttpHeaders();

        if (exc instanceof MissingServletRequestParameterException pexc) {
            return handleMissingServletRequestParameterException(exc, headers, request, pexc.getParameterName());
        } else if (exc instanceof PriceNotFoundException pexc) {
            return handlePriceNotFoundException(pexc, headers, request);
        } else if (exc instanceof InvalidDateException || exc instanceof InvalidBrandException || exc instanceof InvalidProductException) {
            return handleInvalidValuesException(exc, headers, request);
        }

        return handleInternalException(exc, headers, request);
    }

    protected ResponseEntity<String> handlePriceNotFoundException(PriceNotFoundException exc, HttpHeaders headers, WebRequest request) {
        final String errorMessage = "Could not find a price tariff for the requested parameters";
        return new ResponseEntity<>(errorMessage, headers, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<String> handleInvalidValuesException(Exception exc, HttpHeaders headers, WebRequest request) {
        return new ResponseEntity<>(exc.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<String> handleMissingServletRequestParameterException(Exception exc, HttpHeaders headers, WebRequest request, String parameterName) {
        return new ResponseEntity<>(exc.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<String> handleInternalException(Exception exc, HttpHeaders headers, WebRequest request) {
        return new ResponseEntity<>(exc.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
