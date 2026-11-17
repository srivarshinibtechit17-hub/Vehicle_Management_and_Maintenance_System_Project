package com.vehicleservice.model;

/**
 * Domain entity representing a customer's vehicle.
 */
public class Vehicle {
    private String vehicleId;
    private String registrationNo;
    private String model;
    private VehicleType vehicleType;
    private int manufacturingYear;
    private String customerId;

    public Vehicle() {}

    public Vehicle(String vehicleId, String registrationNo, String model, VehicleType vehicleType, int manufacturingYear, String customerId) {
        this.vehicleId = vehicleId;
        this.registrationNo = registrationNo;
        this.model = model;
        this.vehicleType = vehicleType;
        this.manufacturingYear = manufacturingYear;
        this.customerId = customerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return String.format("[%s] Reg: %-13s | Model: %-20s | Type: %-12s | Year: %d | Owner: %s",
                vehicleId, registrationNo, model, vehicleType.name(), manufacturingYear, customerId);
    }
}
