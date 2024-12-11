package com.inditex.prices;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import com.inditex.prices.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
    @ExceptionHandler({PriceNotFoundException.class})
    protected ResponseEntity<String> handlePriceNotFoundException(PriceNotFoundException exc, WebRequest request) {
        log.debug("Handling " + exc.getClass().getCanonicalName());
        final String errorMessage = "Could not find a price tariff for the requested parameters";
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidDateException.class, InvalidBrandException.class, InvalidProductException.class})
    protected ResponseEntity<String> handleInvalidValuesException(Exception exc, WebRequest request) {
        log.debug("Handling " + exc.getClass().getCanonicalName());
        return new ResponseEntity<>(exc.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<String> handleMissingServletRequestParameterException(Exception exc, WebRequest request) {
        log.debug("Handling " + exc.getClass().getCanonicalName());
        return new ResponseEntity<>(exc.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<String> handleInternalException(Exception exc, WebRequest request) {
        log.debug("Handling " + exc.getClass().getCanonicalName());
        return new ResponseEntity<>(exc.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
