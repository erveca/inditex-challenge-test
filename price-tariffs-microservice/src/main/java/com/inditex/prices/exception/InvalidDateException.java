package com.inditex.prices.exception;

/**
 * This exception indicates a Date is not valid.
 */
public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
}
