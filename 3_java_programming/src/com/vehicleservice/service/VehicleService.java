package com.vehicleservice.service;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.exception.InvalidDataException;
import com.vehicleservice.exception.VehicleNotFoundException;
import com.vehicleservice.model.Customer;
import com.vehicleservice.model.Vehicle;
import com.vehicleservice.model.VehicleType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business service managing vehicle registration and retrieval (FR-02).
 */
public class VehicleService {
    private final DatabaseManager db = DatabaseManager.getInstance();

    public Vehicle registerVehicle(String regNo, String model, VehicleType type, int year, String customerId) 
            throws InvalidDataException {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new InvalidDataException("Vehicle registration number cannot be empty.");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new InvalidDataException("Vehicle model name cannot be empty.");
        }
        if (year < 1980 || year > 2027) {
            throw new InvalidDataException("Manufacturing year must be between 1980 and 2027.");
        }
        Customer owner = db.getCustomer(customerId);
        if (owner == null) {
            throw new InvalidDataException("Owner Customer ID " + customerId + " does not exist.");
        }
        if (db.getVehicleByRegNo(regNo) != null) {
            throw new InvalidDataException("Vehicle with registration " + regNo + " is already registered.");
        }

        String id = db.generateVehicleId();
        Vehicle vehicle = new Vehicle(id, regNo.trim().toUpperCase(), model.trim(), type, year, customerId);
        db.addVehicle(vehicle);
        return vehicle;
    }

    public Vehicle getVehicleById(String vehicleId) throws VehicleNotFoundException {
        Vehicle v = db.getVehicle(vehicleId);
        if (v == null) {
            throw new VehicleNotFoundException("Vehicle not found for ID: " + vehicleId);
        }
        return v;
    }

    public Vehicle getVehicleByRegNo(String regNo) throws VehicleNotFoundException {
        Vehicle v = db.getVehicleByRegNo(regNo);
        if (v == null) {
            throw new VehicleNotFoundException("Vehicle not found for Registration Number: " + regNo);
        }
        return v;
    }

    public List<Vehicle> getVehiclesByCustomerId(String customerId) {
        return db.getAllVehicles().stream()
                .filter(v -> v.getCustomerId().equalsIgnoreCase(customerId))
                .collect(Collectors.toList());
    }

    public Collection<Vehicle> getAllVehicles() {
        return db.getAllVehicles();
    }
}
