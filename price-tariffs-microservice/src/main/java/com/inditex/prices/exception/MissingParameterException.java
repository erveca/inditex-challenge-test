package com.inditex.prices.exception;

/**
 * This exception indicates a parameter is missing.
 */
public class MissingParameterException extends Exception {
    public MissingParameterException(String message) {
        super(message);
    }
}
