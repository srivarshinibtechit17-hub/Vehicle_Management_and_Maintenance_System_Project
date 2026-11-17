package com.vehicleservice.model;

import java.time.LocalDate;

/**
 * Domain entity representing an automobile owner / customer.
 */
public class Customer {
    private String customerId;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private LocalDate registeredDate;

    public Customer() {
        this.registeredDate = LocalDate.now();
    }

    public Customer(String customerId, String fullName, String phone, String email, String address) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registeredDate = LocalDate.now();
    }

    public Customer(String customerId, String fullName, String phone, String email, String address, LocalDate registeredDate) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registeredDate = registeredDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-18s | Phone: %-12s | Email: %-22s | Address: %s",
                customerId, fullName, phone, email, address);
    }
}
