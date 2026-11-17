package com.vehicleservice.service;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.exception.InsufficientStockException;
import com.vehicleservice.exception.InvalidDataException;
import com.vehicleservice.model.*;
import java.util.Collection;
import java.util.Map;

/**
 * Business service managing service execution, parts allocation, and labor calculation (FR-04).
 */
public class MaintenanceService {
    private final DatabaseManager db = DatabaseManager.getInstance();

    public ServiceRecord startService(String appointmentId, String technicianName, String workDescription,
                                      double laborHours, double hourlyRate) throws InvalidDataException {
        ServiceAppointment apt = db.getAppointment(appointmentId);
        if (apt == null) {
            throw new InvalidDataException("Appointment " + appointmentId + " not found.");
        }
        if (laborHours <= 0) {
            throw new InvalidDataException("Labor hours must be greater than zero.");
        }

        String recordId = db.generateRecordId();
        ServiceRecord record = new ServiceRecord(recordId, appointmentId, apt.getVehicleId(),
                technicianName, workDescription, laborHours, hourlyRate);
        record.setServiceStatus(ServiceStatus.IN_PROGRESS);
        apt.setStatus(ServiceStatus.IN_PROGRESS);

        db.addServiceRecord(record);
        return record;
    }

    public synchronized void allocateSparePart(String recordId, String partId, int quantity)
            throws InvalidDataException, InsufficientStockException {
        ServiceRecord record = db.getServiceRecord(recordId);
        if (record == null) {
            throw new InvalidDataException("Service record " + recordId + " does not exist.");
        }
        SparePart part = db.getSparePart(partId);
        if (part == null) {
            throw new InvalidDataException("Spare part " + partId + " not found in inventory.");
        }
        if (quantity <= 0) {
            throw new InvalidDataException("Part quantity must be at least 1.");
        }
        if (part.getStockQuantity() < quantity) {
            throw new InsufficientStockException(String.format("Insufficient stock for %s (%s). Available: %d, Requested: %d",
                    part.getPartName(), partId, part.getStockQuantity(), quantity));
        }

        part.deductStock(quantity);
        record.addPart(part, quantity);
    }

    public void completeService(String recordId) throws InvalidDataException {
        ServiceRecord record = db.getServiceRecord(recordId);
        if (record == null) {
            throw new InvalidDataException("Service record " + recordId + " does not exist.");
        }
        record.setServiceStatus(ServiceStatus.COMPLETED);

        ServiceAppointment apt = db.getAppointment(record.getAppointmentId());
        if (apt != null) {
            apt.setStatus(ServiceStatus.COMPLETED);
        }
    }

    public ServiceRecord getRecordById(String recordId) {
        return db.getServiceRecord(recordId);
    }

    public Collection<ServiceRecord> getAllRecords() {
        return db.getAllServiceRecords();
    }
}
