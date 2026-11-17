package com.vehicleservice.service;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.exception.InvalidDataException;
import com.vehicleservice.model.Customer;
import java.util.Collection;

/**
 * Business service managing customer registration and lookup (FR-01).
 */
public class CustomerService {
    private final DatabaseManager db = DatabaseManager.getInstance();

    public Customer registerCustomer(String fullName, String phone, String email, String address) throws InvalidDataException {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new InvalidDataException("Customer full name cannot be blank.");
        }
        if (phone == null || !phone.trim().matches("\\d{10}")) {
            throw new InvalidDataException("Invalid phone number! Must be exactly 10 numeric digits.");
        }
        if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
            throw new InvalidDataException("Invalid email format! Must contain '@'.");
        }

        // Check phone uniqueness
        for (Customer existing : db.getAllCustomers()) {
            if (existing.getPhone().equals(phone.trim())) {
                throw new InvalidDataException("Customer with phone " + phone + " already registered under ID " + existing.getCustomerId());
            }
        }

        String id = db.generateCustomerId();
        Customer customer = new Customer(id, fullName.trim(), phone.trim(), email != null ? email.trim() : "", address != null ? address.trim() : "");
        db.addCustomer(customer);
        return customer;
    }

    public Customer getCustomerById(String customerId) {
        return db.getCustomer(customerId);
    }

    public Collection<Customer> getAllCustomers() {
        return db.getAllCustomers();
    }
}
