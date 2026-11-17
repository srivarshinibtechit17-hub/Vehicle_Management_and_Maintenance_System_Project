package com.vehicleservice.model;

import java.time.LocalDate;

/**
 * Domain entity representing an itemized tax invoice.
 */
public class Bill {
    private String billId;
    private String recordId;
    private String vehicleId;
    private String customerId;
    private LocalDate billDate;
    private double partsCostTotal;
    private double laborChargesTotal;
    private double subtotal;
    private double taxRate; // e.g. 18.0 for 18% GST
    private double taxAmount;
    private double discountAmount;
    private double totalAmount;
    private String paymentStatus;
    private String paymentMode;

    public Bill() {
        this.billDate = LocalDate.now();
        this.taxRate = 18.0;
        this.paymentStatus = "PAID";
        this.paymentMode = "CASH";
    }

    public Bill(String billId, String recordId, String vehicleId, String customerId, 
                double partsCostTotal, double laborChargesTotal, double discountAmount, String paymentMode) {
        this.billId = billId;
        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.billDate = LocalDate.now();
        this.partsCostTotal = partsCostTotal;
        this.laborChargesTotal = laborChargesTotal;
        this.discountAmount = discountAmount;
        this.paymentMode = paymentMode;
        this.taxRate = 18.0;
        this.paymentStatus = "PAID";
        calculate();
    }

    public void calculate() {
        this.subtotal = this.partsCostTotal + this.laborChargesTotal;
        double taxableAmount = Math.max(0, this.subtotal - this.discountAmount);
        this.taxAmount = taxableAmount * (this.taxRate / 100.0);
        this.totalAmount = taxableAmount + this.taxAmount;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public double getPartsCostTotal() {
        return partsCostTotal;
    }

    public void setPartsCostTotal(double partsCostTotal) {
        this.partsCostTotal = partsCostTotal;
        calculate();
    }

    public double getLaborChargesTotal() {
        return laborChargesTotal;
    }

    public void setLaborChargesTotal(double laborChargesTotal) {
        this.laborChargesTotal = laborChargesTotal;
        calculate();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
        calculate();
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
        calculate();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String generatePrintableInvoice() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================================\n");
        sb.append("               AUTO-CARE SERVICE CENTER TAX INVOICE                 \n");
        sb.append("====================================================================\n");
        sb.append(String.format(" Invoice No   : %-20s Date: %s\n", billId, billDate));
        sb.append(String.format(" Service ID   : %-20s Vehicle ID: %s\n", recordId, vehicleId));
        sb.append(String.format(" Customer ID  : %-20s Payment Mode: %s\n", customerId, paymentMode));
        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format(" Spare Parts Charges                     : ₹ %12.2f\n", partsCostTotal));
        sb.append(String.format(" Labor & Workmanship Charges             : ₹ %12.2f\n", laborChargesTotal));
        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format(" Subtotal                                : ₹ %12.2f\n", subtotal));
        sb.append(String.format(" Promotional Discount Applied            : ₹ %12.2f\n", discountAmount));
        sb.append(String.format(" GST Tax (%.1f%%)                         : ₹ %12.2f\n", taxRate, taxAmount));
        sb.append("====================================================================\n");
        sb.append(String.format(" TOTAL INVOICE PAYABLE                   : ₹ %12.2f\n", totalAmount));
        sb.append(String.format(" Payment Status                          : %s\n", paymentStatus));
        sb.append("====================================================================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("[%s] Record: %-8s | Parts: ₹%-8.2f | Labor: ₹%-8.2f | Tax: ₹%-7.2f | TOTAL: ₹%-9.2f (%s)",
                billId, recordId, partsCostTotal, laborChargesTotal, taxAmount, totalAmount, paymentStatus);
    }
}
