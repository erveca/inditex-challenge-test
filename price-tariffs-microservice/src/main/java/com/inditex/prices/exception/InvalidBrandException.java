package com.inditex.prices.exception;

/**
 * This exception indicates a Brand is not valid.
 */
public class InvalidBrandException extends Exception {
    public InvalidBrandException(String message) {
        super(message);
    }
}
