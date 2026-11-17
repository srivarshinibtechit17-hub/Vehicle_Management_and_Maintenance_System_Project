package com.vehicleservice.exception;

/**
 * Thrown when an appointment booking conflicts with an already reserved slot.
 */
public class AppointmentConflictException extends Exception {
    public AppointmentConflictException(String message) {
        super(message);
    }
}
