package com.vehicleservice.service;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.exception.AppointmentConflictException;
import com.vehicleservice.exception.InvalidDataException;
import com.vehicleservice.exception.VehicleNotFoundException;
import com.vehicleservice.model.ServiceAppointment;
import com.vehicleservice.model.ServiceStatus;
import com.vehicleservice.model.Vehicle;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business service managing appointment scheduling, validation, and collision avoidance (FR-03).
 */
public class AppointmentService {
    private final DatabaseManager db = DatabaseManager.getInstance();

    public ServiceAppointment scheduleAppointment(String vehicleId, LocalDate date, String timeSlot, String requestedServiceType)
            throws InvalidDataException, VehicleNotFoundException, AppointmentConflictException {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidDataException("Appointment date cannot be in the past.");
        }
        if (timeSlot == null || timeSlot.trim().isEmpty()) {
            throw new InvalidDataException("Time slot cannot be empty.");
        }
        if (requestedServiceType == null || requestedServiceType.trim().isEmpty()) {
            throw new InvalidDataException("Requested service type cannot be empty.");
        }

        Vehicle vehicle = db.getVehicle(vehicleId);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Cannot schedule appointment: Vehicle ID " + vehicleId + " does not exist.");
        }

        // Slot collision check: Max 3 vehicles per time slot on any given day
        long bookingsInSlot = db.getAllAppointments().stream()
                .filter(a -> a.getAppointmentDate().equals(date) &&
                             a.getTimeSlot().equalsIgnoreCase(timeSlot) &&
                             a.getStatus() != ServiceStatus.CANCELLED)
                .count();

        if (bookingsInSlot >= 3) {
            throw new AppointmentConflictException(
                    String.format("Service capacity exceeded for %s at %s. Please select another slot.", date, timeSlot));
        }

        String aptId = db.generateAppointmentId();
        ServiceAppointment appointment = new ServiceAppointment(
                aptId, vehicleId, vehicle.getCustomerId(), date, timeSlot, requestedServiceType, ServiceStatus.SCHEDULED);
        db.addAppointment(appointment);
        return appointment;
    }

    public ServiceAppointment getAppointmentById(String aptId) {
        return db.getAppointment(aptId);
    }

    public List<ServiceAppointment> getPendingAppointments() {
        return db.getAllAppointments().stream()
                .filter(a -> a.getStatus() == ServiceStatus.SCHEDULED)
                .collect(Collectors.toList());
    }

    public Collection<ServiceAppointment> getAllAppointments() {
        return db.getAllAppointments();
    }
}
