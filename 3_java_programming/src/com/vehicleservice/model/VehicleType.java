package com.vehicleservice.model;

/**
 * Enumeration representing supported motor vehicle classifications.
 */
public enum VehicleType {
    SEDAN("Sedan"),
    SUV("Sports Utility Vehicle"),
    HATCHBACK("Hatchback"),
    TRUCK("Light Commercial Truck"),
    TWO_WHEELER("Motorcycle / Scooter");

    private final String displayName;

    VehicleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static VehicleType fromString(String text) {
        for (VehicleType vt : VehicleType.values()) {
            if (vt.name().equalsIgnoreCase(text) || vt.displayName.equalsIgnoreCase(text)) {
                return vt;
            }
        }
        return SEDAN; // default fallback
    }
}
