package com.inditex.prices.exception;

/**
 * This exception indicates a Product is not valid.
 */
public class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}
