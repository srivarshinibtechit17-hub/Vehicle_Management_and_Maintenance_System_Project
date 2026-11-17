package com.vehicleservice.model;

/**
 * Domain entity representing an automotive spare part in workshop inventory.
 */
public class SparePart {
    private String partId;
    private String partName;
    private String category;
    private double unitPrice;
    private int stockQuantity;
    private int reorderLevel;

    public SparePart() {}

    public SparePart(String partId, String partName, String category, double unitPrice, int stockQuantity, int reorderLevel) {
        this.partId = partId;
        this.partName = partName;
        this.category = category;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public synchronized boolean deductStock(int quantity) {
        if (this.stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    public synchronized void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-28s | Category: %-12s | Price: ₹%8.2f | Stock: %3d units",
                partId, partName, category, unitPrice, stockQuantity);
    }
}
