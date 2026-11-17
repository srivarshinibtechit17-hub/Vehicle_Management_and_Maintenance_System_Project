package com.vehicleservice.dao;

import com.vehicleservice.model.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe singleton repository providing database persistence,
 * relational mapping, and CSV export integration with Python Data Science tier.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Vehicle> vehicles = new ConcurrentHashMap<>();
    private final Map<String, SparePart> spareParts = new ConcurrentHashMap<>();
    private final Map<String, ServiceAppointment> appointments = new ConcurrentHashMap<>();
    private final Map<String, ServiceRecord> serviceRecords = new ConcurrentHashMap<>();
    private final Map<String, Bill> bills = new ConcurrentHashMap<>();

    private int customerCounter = 1010;
    private int vehicleCounter = 115;
    private int appointmentCounter = 315;
    private int recordCounter = 514;
    private int billCounter = 814;

    private DatabaseManager() {
        seedInitialData();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void seedInitialData() {
        // Pre-seed Customers
        addCustomer(new Customer("CUST-1001", "Arun Kumar", "9842100001", "arun.kumar@gmail.com", "12 Perundurai Road, Erode"));
        addCustomer(new Customer("CUST-1002", "Priya Dharshini", "9842100002", "priya.d@yahoo.com", "45 Brough Road, Erode"));
        addCustomer(new Customer("CUST-1003", "Karthik Raja", "9842100003", "karthik.raja@outlook.com", "88 Gandhipuram, Coimbatore"));
        addCustomer(new Customer("CUST-1004", "Sneha Lakshmi", "9842100004", "sneha.l@gmail.com", "104 Mettur Road, Erode"));
        addCustomer(new Customer("CUST-1005", "Venkatesh S", "9842100005", "venkat.s@gmail.com", "23 EVN Road, Erode"));
        addCustomer(new Customer("CUST-1006", "Divya Bharathi", "9842100006", "divya.b@hotmail.com", "78 Thindal, Erode"));
        addCustomer(new Customer("CUST-1007", "Manoj Prabhakar", "9842100007", "manoj.p@gmail.com", "56 Collectorate Post, Salem"));
        addCustomer(new Customer("CUST-1008", "Kavitha R", "9842100008", "kavitha.r@gmail.com", "91 Kollampalayam, Erode"));
        addCustomer(new Customer("CUST-1009", "Suresh Babu", "9842100009", "suresh.babu@gmail.com", "14 Chithode, Erode"));
        addCustomer(new Customer("CUST-1010", "Deepa Shankar", "9842100010", "deepa.s@gmail.com", "33 Nasiyanur Road, Erode"));

        // Pre-seed Vehicles
        addVehicle(new Vehicle("VEH-101", "TN-33-AX-1001", "Hyundai Creta", VehicleType.SUV, 2022, "CUST-1001"));
        addVehicle(new Vehicle("VEH-102", "TN-33-BF-2045", "Maruti Suzuki Swift", VehicleType.HATCHBACK, 2021, "CUST-1002"));
        addVehicle(new Vehicle("VEH-103", "TN-38-CC-3344", "Honda City", VehicleType.SEDAN, 2023, "CUST-1003"));
        addVehicle(new Vehicle("VEH-104", "TN-33-DZ-9080", "Tata Nexon EV", VehicleType.SUV, 2024, "CUST-1004"));
        addVehicle(new Vehicle("VEH-105", "TN-33-ER-4455", "Royal Enfield Classic 350", VehicleType.TWO_WHEELER, 2022, "CUST-1005"));
        addVehicle(new Vehicle("VEH-106", "TN-33-GH-5566", "Toyota Innova Crysta", VehicleType.SUV, 2020, "CUST-1001"));
        addVehicle(new Vehicle("VEH-107", "TN-33-JK-7788", "Tata Ace Gold", VehicleType.TRUCK, 2019, "CUST-1007"));
        addVehicle(new Vehicle("VEH-108", "TN-30-LM-9900", "Hyundai i20", VehicleType.HATCHBACK, 2021, "CUST-1006"));
        addVehicle(new Vehicle("VEH-109", "TN-33-NP-1122", "Mahindra Scorpio-N", VehicleType.SUV, 2023, "CUST-1008"));
        addVehicle(new Vehicle("VEH-110", "TN-33-RS-3344", "Yamaha MT-15", VehicleType.TWO_WHEELER, 2023, "CUST-1005"));
        addVehicle(new Vehicle("VEH-111", "TN-38-TV-5566", "Skoda Slavia", VehicleType.SEDAN, 2022, "CUST-1003"));
        addVehicle(new Vehicle("VEH-112", "TN-33-WX-7788", "Ashok Leyland Dost", VehicleType.TRUCK, 2021, "CUST-1007"));
        addVehicle(new Vehicle("VEH-113", "TN-33-YZ-9900", "Kia Seltos", VehicleType.SUV, 2022, "CUST-1009"));
        addVehicle(new Vehicle("VEH-114", "TN-33-AA-1234", "Honda Activa 6G", VehicleType.TWO_WHEELER, 2024, "CUST-1010"));
        addVehicle(new Vehicle("VEH-115", "TN-33-BC-5678", "Maruti Baleno", VehicleType.HATCHBACK, 2020, "CUST-1002"));

        // Pre-seed Spare Parts
        addSparePart(new SparePart("PRT-01", "Synthetic Engine Oil (3.5L)", "Fluids", 1850.00, 45, 10));
        addSparePart(new SparePart("PRT-02", "Oil Filter Element", "Filters", 350.00, 60, 15));
        addSparePart(new SparePart("PRT-03", "Front Brake Pads Set", "Brakes", 1600.00, 25, 8));
        addSparePart(new SparePart("PRT-04", "Rear Brake Shoes", "Brakes", 1100.00, 20, 5));
        addSparePart(new SparePart("PRT-05", "Engine Air Filter", "Filters", 480.00, 35, 10));
        addSparePart(new SparePart("PRT-06", "Cabin AC Pollen Filter", "Filters", 550.00, 30, 8));
        addSparePart(new SparePart("PRT-07", "Spark Plugs (Set of 4)", "Electrical", 920.00, 28, 6));
        addSparePart(new SparePart("PRT-08", "Coolant Fluid (1L)", "Fluids", 380.00, 50, 12));
        addSparePart(new SparePart("PRT-09", "Wiper Blades Pair", "Accessories", 650.00, 40, 10));
        addSparePart(new SparePart("PRT-10", "Clutch Plate Assembly", "Transmission", 4200.00, 12, 4));
        addSparePart(new SparePart("PRT-11", "Front Shock Absorber", "Suspension", 3100.00, 10, 3));
        addSparePart(new SparePart("PRT-12", "Battery 12V 45Ah", "Electrical", 4800.00, 15, 4));
        addSparePart(new SparePart("PRT-13", "Brake Fluid DOT 4 (500ml)", "Fluids", 280.00, 40, 10));
        addSparePart(new SparePart("PRT-14", "Headlamp LED Bulb", "Electrical", 750.00, 30, 8));
        addSparePart(new SparePart("PRT-15", "Drive Belt", "Engine", 850.00, 18, 5));

        // Seed sample appointments, services and bills
        seedServicesAndBills();
    }

    private void seedServicesAndBills() {
        createHistoricalService("APT-301", "VEH-101", "CUST-1001", LocalDate.of(2026, 8, 5), "09:00 AM",
                "Periodic Maintenance", "SRV-501", "S. Murugan", "Engine oil flushed, oil filter renewed, 40-pt check.", 2.5,
                Map.of("PRT-01", 1, "PRT-02", 1, "PRT-05", 1), "BIL-801", 0.0, "UPI");

        createHistoricalService("APT-302", "VEH-102", "CUST-1002", LocalDate.of(2026, 8, 8), "11:00 AM",
                "Brake Repair", "SRV-502", "R. Saravanan", "Brake pads renewed, calipers serviced, brake fluid bled.", 2.0,
                Map.of("PRT-03", 1, "PRT-13", 1), "BIL-802", 100.0, "CARD");

        createHistoricalService("APT-303", "VEH-103", "CUST-1003", LocalDate.of(2026, 8, 12), "02:00 PM",
                "Periodic Maintenance", "SRV-503", "K. Prakash", "Full service, oil and spark plugs renewed.", 3.0,
                Map.of("PRT-01", 1, "PRT-02", 1, "PRT-07", 1), "BIL-803", 200.0, "CARD");

        createHistoricalService("APT-304", "VEH-104", "CUST-1004", LocalDate.of(2026, 8, 19), "09:00 AM",
                "AC Overhaul", "SRV-504", "M. Vijay", "AC pollen filter renewed, cooling coil deodorized.", 2.0,
                Map.of("PRT-06", 1), "BIL-804", 0.0, "UPI");

        createHistoricalService("APT-305", "VEH-105", "CUST-1005", LocalDate.of(2026, 8, 25), "11:00 AM",
                "General Service", "SRV-505", "S. Murugan", "Two-wheeler full service, chain lubed, oil renewed.", 1.5,
                Map.of("PRT-01", 1), "BIL-805", 50.0, "CASH");

        createHistoricalService("APT-306", "VEH-106", "CUST-1001", LocalDate.of(2026, 9, 2), "09:00 AM",
                "Clutch Replacement", "SRV-506", "R. Saravanan", "Clutch plate assembly & coolant flushed.", 5.0,
                Map.of("PRT-10", 1, "PRT-08", 1), "BIL-806", 300.0, "NET_BANKING");

        createHistoricalService("APT-307", "VEH-107", "CUST-1007", LocalDate.of(2026, 9, 5), "02:00 PM",
                "Major Overhaul", "SRV-507", "T. Dinesh", "Commercial truck overhaul, drive belt, oil renewed.", 4.5,
                Map.of("PRT-01", 2, "PRT-15", 1), "BIL-807", 0.0, "CARD");

        createHistoricalService("APT-308", "VEH-108", "CUST-1006", LocalDate.of(2026, 9, 12), "10:00 AM",
                "Periodic Maintenance", "SRV-508", "K. Prakash", "Periodic maintenance, wiper pair replaced.", 2.5,
                Map.of("PRT-01", 1, "PRT-02", 1, "PRT-09", 1), "BIL-808", 150.0, "UPI");

        createHistoricalService("APT-309", "VEH-109", "CUST-1008", LocalDate.of(2026, 9, 18), "02:00 PM",
                "Suspension Repair", "SRV-509", "R. Saravanan", "Front suspension shock absorbers renewed.", 4.0,
                Map.of("PRT-11", 2), "BIL-809", 400.0, "CARD");

        createHistoricalService("APT-310", "VEH-110", "CUST-1005", LocalDate.of(2026, 9, 24), "11:00 AM",
                "Electrical Inspection", "SRV-510", "M. Vijay", "Battery replaced, new LED headlamp bulb installed.", 1.5,
                Map.of("PRT-12", 1, "PRT-14", 1), "BIL-810", 100.0, "UPI");

        createHistoricalService("APT-311", "VEH-111", "CUST-1003", LocalDate.of(2026, 10, 2), "09:00 AM",
                "Periodic Maintenance", "SRV-511", "S. Murugan", "Synthetic oil renewed, cabin filter & brake fluid.", 3.0,
                Map.of("PRT-01", 1, "PRT-06", 1, "PRT-13", 1), "BIL-811", 0.0, "UPI");

        createHistoricalService("APT-312", "VEH-112", "CUST-1007", LocalDate.of(2026, 10, 10), "02:00 PM",
                "Brake Service", "SRV-512", "T. Dinesh", "Rear brake shoes replaced, wheel drum polished.", 3.5,
                Map.of("PRT-04", 2), "BIL-812", 150.0, "CASH");

        createHistoricalService("APT-313", "VEH-113", "CUST-1009", LocalDate.of(2026, 10, 18), "10:00 AM",
                "Oil Change", "SRV-513", "K. Prakash", "Engine oil renewed, filter changed, tire check.", 2.0,
                Map.of("PRT-01", 1, "PRT-02", 1), "BIL-813", 0.0, "CARD");

        createHistoricalService("APT-314", "VEH-101", "CUST-1001", LocalDate.of(2026, 11, 5), "09:00 AM",
                "Pre-Trip Inspection", "SRV-514", "S. Murugan", "Wiper pair replaced, coolant topped up.", 1.8,
                Map.of("PRT-09", 1, "PRT-08", 1), "BIL-814", 50.0, "UPI");
    }

    private void createHistoricalService(String aptId, String vehId, String custId, LocalDate date, String slot,
                                        String serviceType, String recId, String tech, String work, double hours,
                                        Map<String, Integer> parts, String billId, double discount, String payMode) {
        ServiceAppointment apt = new ServiceAppointment(aptId, vehId, custId, date, slot, serviceType, ServiceStatus.COMPLETED);
        appointments.put(aptId, apt);

        ServiceRecord rec = new ServiceRecord(recId, aptId, vehId, tech, work, hours, 500.00);
        rec.setServiceStatus(ServiceStatus.COMPLETED);
        rec.setServiceDate(date.atTime(14, 0));

        double partsCost = 0.0;
        for (Map.Entry<String, Integer> entry : parts.entrySet()) {
            SparePart part = spareParts.get(entry.getKey());
            if (part != null) {
                rec.addPart(part, entry.getValue());
                partsCost += (part.getUnitPrice() * entry.getValue());
            }
        }
        serviceRecords.put(recId, rec);

        Bill bill = new Bill(billId, recId, vehId, custId, partsCost, rec.getLaborCharges(), discount, payMode);
        bill.setBillDate(date);
        bills.put(billId, bill);
    }

    // Customer methods
    public synchronized String generateCustomerId() {
        return "CUST-" + (++customerCounter);
    }

    public void addCustomer(Customer c) {
        customers.put(c.getCustomerId(), c);
    }

    public Customer getCustomer(String id) {
        return customers.get(id);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    // Vehicle methods
    public synchronized String generateVehicleId() {
        return "VEH-" + (++vehicleCounter);
    }

    public void addVehicle(Vehicle v) {
        vehicles.put(v.getVehicleId(), v);
    }

    public Vehicle getVehicle(String id) {
        return vehicles.get(id);
    }

    public Vehicle getVehicleByRegNo(String regNo) {
        return vehicles.values().stream()
                .filter(v -> v.getRegistrationNo().equalsIgnoreCase(regNo.trim()))
                .findFirst().orElse(null);
    }

    public Collection<Vehicle> getAllVehicles() {
        return vehicles.values();
    }

    // Spare parts methods
    public void addSparePart(SparePart sp) {
        spareParts.put(sp.getPartId(), sp);
    }

    public SparePart getSparePart(String id) {
        return spareParts.get(id);
    }

    public Collection<SparePart> getAllSpareParts() {
        return spareParts.values();
    }

    // Appointment methods
    public synchronized String generateAppointmentId() {
        return "APT-" + (++appointmentCounter);
    }

    public void addAppointment(ServiceAppointment apt) {
        appointments.put(apt.getAppointmentId(), apt);
    }

    public ServiceAppointment getAppointment(String id) {
        return appointments.get(id);
    }

    public Collection<ServiceAppointment> getAllAppointments() {
        return appointments.values();
    }

    // Service Record methods
    public synchronized String generateRecordId() {
        return "SRV-" + (++recordCounter);
    }

    public void addServiceRecord(ServiceRecord sr) {
        serviceRecords.put(sr.getRecordId(), sr);
    }

    public ServiceRecord getServiceRecord(String id) {
        return serviceRecords.get(id);
    }

    public Collection<ServiceRecord> getAllServiceRecords() {
        return serviceRecords.values();
    }

    // Bill methods
    public synchronized String generateBillId() {
        return "BIL-" + (++billCounter);
    }

    public void addBill(Bill bill) {
        bills.put(bill.getBillId(), bill);
    }

    public Bill getBill(String id) {
        return bills.get(id);
    }

    public Bill getBillByRecordId(String recordId) {
        return bills.values().stream()
                .filter(b -> b.getRecordId().equalsIgnoreCase(recordId))
                .findFirst().orElse(null);
    }

    public Collection<Bill> getAllBills() {
        return bills.values();
    }

    /**
     * Exports all database entities into normalized CSV files for the Python Data Science pipeline.
     */
    public boolean exportToCsv(String targetDirPath) {
        File dir = new File(targetDirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 1. Export Customers
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir, "customers.csv")))) {
                bw.write("customer_id,full_name,phone,email,address,registered_date\n");
                for (Customer c : customers.values()) {
                    bw.write(String.format("%s,\"%s\",%s,%s,\"%s\",%s\n",
                            c.getCustomerId(), c.getFullName(), c.getPhone(), c.getEmail(), c.getAddress(), c.getRegisteredDate()));
                }
            }

            // 2. Export Vehicles
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir, "vehicles.csv")))) {
                bw.write("vehicle_id,registration_no,model,vehicle_type,manufacturing_year,customer_id\n");
                for (Vehicle v : vehicles.values()) {
                    bw.write(String.format("%s,%s,\"%s\",%s,%d,%s\n",
                            v.getVehicleId(), v.getRegistrationNo(), v.getModel(), v.getVehicleType().name(), v.getManufacturingYear(), v.getCustomerId()));
                }
            }

            // 3. Export Service Records & Invoices (Consolidated Analysis Dataset)
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir, "vehicle_maintenance_dataset.csv")))) {
                bw.write("record_id,appointment_id,vehicle_id,registration_no,vehicle_type,model,customer_id," +
                        "service_date,service_type,technician_name,labor_hours,labor_charges,parts_cost,subtotal," +
                        "tax_amount,discount_amount,total_amount,payment_mode,payment_status\n");
                for (ServiceRecord sr : serviceRecords.values()) {
                    Vehicle v = vehicles.get(sr.getVehicleId());
                    ServiceAppointment apt = appointments.get(sr.getAppointmentId());
                    Bill b = getBillByRecordId(sr.getRecordId());

                    String regNo = (v != null) ? v.getRegistrationNo() : "UNKNOWN";
                    String vType = (v != null) ? v.getVehicleType().name() : "SEDAN";
                    String model = (v != null) ? v.getModel() : "Standard";
                    String custId = (v != null) ? v.getCustomerId() : "CUST-1001";
                    String sType = (apt != null) ? apt.getRequestedServiceType() : "General";

                    double partsCost = (b != null) ? b.getPartsCostTotal() : sr.calculateTotalPartsCost();
                    double laborCharges = (b != null) ? b.getLaborChargesTotal() : sr.getLaborCharges();
                    double subtotal = (b != null) ? b.getSubtotal() : (partsCost + laborCharges);
                    double tax = (b != null) ? b.getTaxAmount() : (subtotal * 0.18);
                    double discount = (b != null) ? b.getDiscountAmount() : 0.0;
                    double total = (b != null) ? b.getTotalAmount() : (subtotal + tax - discount);
                    String payMode = (b != null) ? b.getPaymentMode() : "CASH";
                    String payStatus = (b != null) ? b.getPaymentStatus() : "PAID";

                    bw.write(String.format("%s,%s,%s,%s,%s,\"%s\",%s,%s,\"%s\",\"%s\",%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%s,%s\n",
                            sr.getRecordId(), sr.getAppointmentId(), sr.getVehicleId(), regNo, vType, model, custId,
                            sr.getServiceDate().toLocalDate(), sType, sr.getTechnicianName(), sr.getLaborHours(),
                            laborCharges, partsCost, subtotal, tax, discount, total, payMode, payStatus));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing CSV files: " + e.getMessage());
            return false;
        }
    }
}
