package com.vehicleservice.exception;

/**
 * Thrown when an inventory deduction exceeds available stock quantity of a spare part.
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}
