package com.vehicleservice.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain entity representing an executed maintenance service session.
 */
public class ServiceRecord {
    private String recordId;
    private String appointmentId;
    private String vehicleId;
    private String technicianName;
    private String workPerformed;
    private double laborHours;
    private double laborHourlyRate;
    private double laborCharges;
    private Map<SparePart, Integer> partsUsed;
    private ServiceStatus serviceStatus;
    private LocalDateTime serviceDate;

    public ServiceRecord() {
        this.partsUsed = new HashMap<>();
        this.serviceStatus = ServiceStatus.IN_PROGRESS;
        this.serviceDate = LocalDateTime.now();
        this.laborHourlyRate = 500.00;
    }

    public ServiceRecord(String recordId, String appointmentId, String vehicleId, String technicianName, 
                         String workPerformed, double laborHours, double laborHourlyRate) {
        this.recordId = recordId;
        this.appointmentId = appointmentId;
        this.vehicleId = vehicleId;
        this.technicianName = technicianName;
        this.workPerformed = workPerformed;
        this.laborHours = laborHours;
        this.laborHourlyRate = laborHourlyRate;
        this.laborCharges = laborHours * laborHourlyRate;
        this.partsUsed = new HashMap<>();
        this.serviceStatus = ServiceStatus.IN_PROGRESS;
        this.serviceDate = LocalDateTime.now();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getWorkPerformed() {
        return workPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        this.workPerformed = workPerformed;
    }

    public double getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(double laborHours) {
        this.laborHours = laborHours;
        this.laborCharges = this.laborHours * this.laborHourlyRate;
    }

    public double getLaborHourlyRate() {
        return laborHourlyRate;
    }

    public void setLaborHourlyRate(double laborHourlyRate) {
        this.laborHourlyRate = laborHourlyRate;
        this.laborCharges = this.laborHours * this.laborHourlyRate;
    }

    public double getLaborCharges() {
        return laborCharges;
    }

    public void setLaborCharges(double laborCharges) {
        this.laborCharges = laborCharges;
    }

    public Map<SparePart, Integer> getPartsUsed() {
        return partsUsed;
    }

    public void setPartsUsed(Map<SparePart, Integer> partsUsed) {
        this.partsUsed = partsUsed;
    }

    public void addPart(SparePart part, int quantity) {
        this.partsUsed.put(part, this.partsUsed.getOrDefault(part, 0) + quantity);
    }

    public double calculateTotalPartsCost() {
        return partsUsed.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getUnitPrice() * entry.getValue())
                .sum();
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public LocalDateTime getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDateTime serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Override
    public String toString() {
        return String.format("[%s] Veh: %-8s | Tech: %-14s | Hours: %.1f | Labor: ₹%.2f | Parts: ₹%.2f | Status: %s",
                recordId, vehicleId, technicianName, laborHours, laborCharges, calculateTotalPartsCost(), serviceStatus);
    }
}
