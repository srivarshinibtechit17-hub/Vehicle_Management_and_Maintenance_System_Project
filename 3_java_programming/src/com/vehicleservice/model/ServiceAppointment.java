package com.vehicleservice.model;

import java.time.LocalDate;

/**
 * Domain entity representing a customer service booking.
 */
public class ServiceAppointment {
    private String appointmentId;
    private String vehicleId;
    private String customerId;
    private LocalDate appointmentDate;
    private String timeSlot;
    private String requestedServiceType;
    private ServiceStatus status;

    public ServiceAppointment() {
        this.status = ServiceStatus.SCHEDULED;
    }

    public ServiceAppointment(String appointmentId, String vehicleId, String customerId, LocalDate appointmentDate, String timeSlot, String requestedServiceType) {
        this.appointmentId = appointmentId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.requestedServiceType = requestedServiceType;
        this.status = ServiceStatus.SCHEDULED;
    }

    public ServiceAppointment(String appointmentId, String vehicleId, String customerId, LocalDate appointmentDate, String timeSlot, String requestedServiceType, ServiceStatus status) {
        this.appointmentId = appointmentId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.requestedServiceType = requestedServiceType;
        this.status = status;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getRequestedServiceType() {
        return requestedServiceType;
    }

    public void setRequestedServiceType(String requestedServiceType) {
        this.requestedServiceType = requestedServiceType;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s] Date: %s (%-18s) | Vehicle: %-8s | Service: %-25s | Status: %s",
                appointmentId, appointmentDate, timeSlot, vehicleId, requestedServiceType, status.getDescription());
    }
}
