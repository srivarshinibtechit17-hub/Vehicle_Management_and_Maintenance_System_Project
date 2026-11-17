package com.vehicleservice.exception;

/**
 * Thrown when an operation references a vehicle that is not present in the system registry.
 */
public class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
