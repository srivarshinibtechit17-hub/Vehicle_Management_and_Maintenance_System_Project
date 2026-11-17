package com.vehicleservice.service;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.exception.InvalidDataException;
import com.vehicleservice.model.Bill;
import com.vehicleservice.model.Customer;
import com.vehicleservice.model.ServiceRecord;
import com.vehicleservice.model.ServiceStatus;
import com.vehicleservice.model.Vehicle;
import java.util.Collection;

/**
 * Business service calculating service bills, applying taxes and discounts,
 * and generating itemized customer invoices (FR-05).
 */
public class BillingService {
    private final DatabaseManager db = DatabaseManager.getInstance();

    public Bill generateBill(String recordId, double discountAmount, String paymentMode) 
            throws InvalidDataException {
        ServiceRecord record = db.getServiceRecord(recordId);
        if (record == null) {
            throw new InvalidDataException("Service record " + recordId + " not found.");
        }
        if (record.getServiceStatus() != ServiceStatus.COMPLETED) {
            throw new InvalidDataException("Cannot generate bill: Service record " + recordId + " is not COMPLETED yet.");
        }
        if (db.getBillByRecordId(recordId) != null) {
            throw new InvalidDataException("Bill has already been generated for Service Record " + recordId);
        }
        if (discountAmount < 0) {
            throw new InvalidDataException("Discount amount cannot be negative.");
        }

        Vehicle vehicle = db.getVehicle(record.getVehicleId());
        String customerId = (vehicle != null) ? vehicle.getCustomerId() : "CUST-UNKNOWN";

        double partsCost = record.calculateTotalPartsCost();
        double laborCost = record.getLaborCharges();

        String billId = db.generateBillId();
        Bill bill = new Bill(billId, recordId, record.getVehicleId(), customerId, partsCost, laborCost, discountAmount, paymentMode);
        db.addBill(bill);
        return bill;
    }

    public Bill getBillByRecordId(String recordId) {
        return db.getBillByRecordId(recordId);
    }

    public Collection<Bill> getAllBills() {
        return db.getAllBills();
    }
}
