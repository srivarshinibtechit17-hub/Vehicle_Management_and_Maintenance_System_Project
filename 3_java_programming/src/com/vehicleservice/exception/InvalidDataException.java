package com.vehicleservice.exception;

/**
 * Thrown when domain validation rules are violated by client input data.
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
