package com.vehicleservice;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.exception.*;
import com.vehicleservice.model.*;
import com.vehicleservice.service.*;
import com.vehicleservice.thread.WorkloadMonitorThread;
import com.vehicleservice.util.ConsoleHelper;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;

/**
 * Main Application Controller for Vehicle Service Management and Maintenance Analysis System.
 * Scenario 16 (Register Nos: 16, 46, 76, 106).
 */
public class Main {
    private static final CustomerService customerService = new CustomerService();
    private static final VehicleService vehicleService = new VehicleService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final MaintenanceService maintenanceService = new MaintenanceService();
    private static final BillingService billingService = new BillingService();
    private static final ReportService reportService = new ReportService();

    public static void main(String[] args) {
        ConsoleHelper.printHeader("VEHICLE SERVICE MANAGEMENT & MAINTENANCE ANALYSIS SYSTEM\n" +
                "   Academic Year: 2026-2027 (ODD) | Scenario 16 (Reg: 16, 46, 76, 106)");

        // Start background workload monitoring daemon thread (Multithreading Demonstration)
        WorkloadMonitorThread monitorRunnable = new WorkloadMonitorThread(30000);
        Thread monitorThread = new Thread(monitorRunnable, "WorkloadMonitorThread");
        monitorThread.setDaemon(true);
        monitorThread.setPriority(Thread.MIN_PRIORITY);
        monitorThread.start();

        // Check if automated demo mode is requested via CLI argument
        if (args.length > 0 && args[0].equalsIgnoreCase("--demo")) {
            runAutomatedDemonstration();
            return;
        }

        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = ConsoleHelper.readInt("Enter your choice (1-9)");
            try {
                switch (choice) {
                    case 1:
                        handleCustomerManagement();
                        break;
                    case 2:
                        handleVehicleManagement();
                        break;
                    case 3:
                        handleAppointmentScheduling();
                        break;
                    case 4:
                        handleMaintenanceOperations();
                        break;
                    case 5:
                        handleBillingAndInvoicing();
                        break;
                    case 6:
                        handleManagerialReports();
                        break;
                    case 7:
                        handleExportDataToCsv();
                        break;
                    case 8:
                        runAutomatedDemonstration();
                        break;
                    case 9:
                        exit = true;
                        monitorRunnable.stopMonitoring();
                        System.out.println("\n>> Thank you for using Vehicle Service Management System. Exiting safely...");
                        break;
                    default:
                        System.out.println(">> Invalid choice! Please select an option between 1 and 9.");
                }
            } catch (Exception e) {
                System.out.println("\n>> SYSTEM ERROR: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n========================================================================");
        System.out.println("                         MAIN NAVIGATION MENU                           ");
        System.out.println("========================================================================");
        System.out.println(" 1. Customer Management           (FR-01: Maintain Customer Details)");
        System.out.println(" 2. Vehicle Management            (FR-02: Maintain Vehicle Details)");
        System.out.println(" 3. Service Appointments          (FR-03: Schedule & Track Bookings)");
        System.out.println(" 4. Maintenance Operations        (FR-04: Record Work & Spare Parts)");
        System.out.println(" 5. Billing & Invoicing           (FR-05: Calculate Service Bills)");
        System.out.println(" 6. Operational Reports           (FR-06: History, Costs & Workload)");
        System.out.println(" 7. Export Data to CSV            (FR-07: Sync with Python Pipeline)");
        System.out.println(" 8. Run Automated Demo Workflow   (Simulates Full System Execution)");
        System.out.println(" 9. Exit System");
        System.out.println("========================================================================");
    }

    // -------------------------------------------------------------------------
    // FR-01: Customer Management
    // -------------------------------------------------------------------------
    private static void handleCustomerManagement() {
        ConsoleHelper.printSubHeader("CUSTOMER MANAGEMENT (FR-01)");
        System.out.println("1. Register New Customer");
        System.out.println("2. View All Registered Customers");
        System.out.println("3. Search Customer by ID");
        int sub = ConsoleHelper.readInt("Select option");

        switch (sub) {
            case 1:
                String name = ConsoleHelper.readString("Enter Customer Full Name");
                String phone = ConsoleHelper.readString("Enter 10-Digit Mobile Phone");
                String email = ConsoleHelper.readString("Enter Email Address");
                String address = ConsoleHelper.readString("Enter Residential/Office Address");
                try {
                    Customer c = customerService.registerCustomer(name, phone, email, address);
                    System.out.println(">> SUCCESS: Customer registered successfully!");
                    System.out.println("   Assigned Customer ID: " + c.getCustomerId());
                } catch (InvalidDataException e) {
                    System.out.println(">> VALIDATION ERROR: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("\n--- REGISTERED CUSTOMER DIRECTORY ---");
                Collection<Customer> customers = customerService.getAllCustomers();
                customers.forEach(System.out::println);
                break;
            case 3:
                String cid = ConsoleHelper.readString("Enter Customer ID (e.g. CUST-1001)");
                Customer cust = customerService.getCustomerById(cid);
                if (cust != null) {
                    System.out.println(">> Customer Record: " + cust);
                } else {
                    System.out.println(">> Customer not found.");
                }
                break;
            default:
                System.out.println(">> Invalid option.");
        }
    }

    // -------------------------------------------------------------------------
    // FR-02: Vehicle Management
    // -------------------------------------------------------------------------
    private static void handleVehicleManagement() {
        ConsoleHelper.printSubHeader("VEHICLE MANAGEMENT (FR-02)");
        System.out.println("1. Register New Vehicle");
        System.out.println("2. View All Registered Vehicles");
        System.out.println("3. Search Vehicles by Customer ID");
        int sub = ConsoleHelper.readInt("Select option");

        switch (sub) {
            case 1:
                String regNo = ConsoleHelper.readString("Enter Vehicle Registration No (e.g. TN-33-AA-9999)");
                String model = ConsoleHelper.readString("Enter Vehicle Model (e.g. Honda City)");
                System.out.println("Select Vehicle Type: 1. SEDAN | 2. SUV | 3. HATCHBACK | 4. TRUCK | 5. TWO_WHEELER");
                int tChoice = ConsoleHelper.readInt("Type Choice (1-5)");
                VehicleType vType;
                switch (tChoice) {
                    case 2: vType = VehicleType.SUV; break;
                    case 3: vType = VehicleType.HATCHBACK; break;
                    case 4: vType = VehicleType.TRUCK; break;
                    case 5: vType = VehicleType.TWO_WHEELER; break;
                    default: vType = VehicleType.SEDAN;
                }
                int year = ConsoleHelper.readInt("Enter Manufacturing Year (1980-2027)");
                String custId = ConsoleHelper.readString("Enter Owner Customer ID (e.g. CUST-1001)");
                try {
                    Vehicle v = vehicleService.registerVehicle(regNo, model, vType, year, custId);
                    System.out.println(">> SUCCESS: Vehicle registered successfully!");
                    System.out.println("   Assigned Vehicle ID: " + v.getVehicleId());
                } catch (InvalidDataException e) {
                    System.out.println(">> VALIDATION ERROR: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("\n--- REGISTERED VEHICLE INVENTORY ---");
                vehicleService.getAllVehicles().forEach(System.out::println);
                break;
            case 3:
                String cId = ConsoleHelper.readString("Enter Customer ID");
                List<Vehicle> list = vehicleService.getVehiclesByCustomerId(cId);
                if (list.isEmpty()) {
                    System.out.println(">> No vehicles registered under Customer ID: " + cId);
                } else {
                    list.forEach(System.out::println);
                }
                break;
            default:
                System.out.println(">> Invalid option.");
        }
    }

    // -------------------------------------------------------------------------
    // FR-03: Service Appointments
    // -------------------------------------------------------------------------
    private static void handleAppointmentScheduling() {
        ConsoleHelper.printSubHeader("SERVICE APPOINTMENTS (FR-03)");
        System.out.println("1. Schedule New Service Appointment");
        System.out.println("2. View All Scheduled Appointments");
        int sub = ConsoleHelper.readInt("Select option");

        switch (sub) {
            case 1:
                String vehId = ConsoleHelper.readString("Enter Vehicle ID (e.g. VEH-101)");
                String dateStr = ConsoleHelper.readString("Enter Appointment Date (YYYY-MM-DD)");
                String slot = ConsoleHelper.readString("Enter Time Slot (e.g. 09:00 AM - 11:00 AM)");
                String type = ConsoleHelper.readString("Enter Requested Service Type (e.g. Periodic Maintenance)");

                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    ServiceAppointment apt = appointmentService.scheduleAppointment(vehId, date, slot, type);
                    System.out.println(">> SUCCESS: Appointment booked successfully!");
                    System.out.println("   Booking Reference ID: " + apt.getAppointmentId());
                } catch (DateTimeParseException e) {
                    System.out.println(">> FORMAT ERROR: Please enter date in valid YYYY-MM-DD format.");
                } catch (AppointmentConflictException | VehicleNotFoundException | InvalidDataException e) {
                    System.out.println(">> BOOKING ERROR: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("\n--- CURRENT SERVICE APPOINTMENTS ---");
                appointmentService.getAllAppointments().forEach(System.out::println);
                break;
            default:
                System.out.println(">> Invalid option.");
        }
    }

    // -------------------------------------------------------------------------
    // FR-04: Maintenance Operations
    // -------------------------------------------------------------------------
    private static void handleMaintenanceOperations() {
        ConsoleHelper.printSubHeader("MAINTENANCE OPERATIONS & SPARE PARTS (FR-04)");
        System.out.println("1. Initiate Maintenance for Scheduled Appointment");
        System.out.println("2. Allocate Spare Part to Service Record");
        System.out.println("3. Mark Service as Completed");
        System.out.println("4. View All Service Records");
        int sub = ConsoleHelper.readInt("Select option");

        switch (sub) {
            case 1:
                String aptId = ConsoleHelper.readString("Enter Appointment ID (e.g. APT-301)");
                String tech = ConsoleHelper.readString("Enter Lead Technician Name");
                String work = ConsoleHelper.readString("Enter Description of Work Performed");
                double hours = ConsoleHelper.readDouble("Enter Estimated Labor Hours");
                try {
                    ServiceRecord rec = maintenanceService.startService(aptId, tech, work, hours, 500.00);
                    System.out.println(">> SUCCESS: Service record initiated!");
                    System.out.println("   Assigned Service Record ID: " + rec.getRecordId());
                } catch (InvalidDataException e) {
                    System.out.println(">> ERROR: " + e.getMessage());
                }
                break;
            case 2:
                String recId = ConsoleHelper.readString("Enter Service Record ID (e.g. SRV-501)");
                String partId = ConsoleHelper.readString("Enter Spare Part ID (e.g. PRT-01)");
                int qty = ConsoleHelper.readInt("Enter Quantity to Allocate");
                try {
                    maintenanceService.allocateSparePart(recId, partId, qty);
                    System.out.println(">> SUCCESS: Part allocated and stock decremented successfully.");
                } catch (InsufficientStockException | InvalidDataException e) {
                    System.out.println(">> INVENTORY ERROR: " + e.getMessage());
                }
                break;
            case 3:
                String rId = ConsoleHelper.readString("Enter Service Record ID to Mark Completed");
                try {
                    maintenanceService.completeService(rId);
                    System.out.println(">> SUCCESS: Service Record " + rId + " marked as COMPLETED.");
                } catch (InvalidDataException e) {
                    System.out.println(">> ERROR: " + e.getMessage());
                }
                break;
            case 4:
                System.out.println("\n--- WORKSHOP SERVICE RECORDS ---");
                maintenanceService.getAllRecords().forEach(System.out::println);
                break;
            default:
                System.out.println(">> Invalid option.");
        }
    }

    // -------------------------------------------------------------------------
    // FR-05: Billing & Invoicing
    // -------------------------------------------------------------------------
    private static void handleBillingAndInvoicing() {
        ConsoleHelper.printSubHeader("BILLING & INVOICING (FR-05)");
        System.out.println("1. Generate Bill for Completed Service Record");
        System.out.println("2. View All Generated Bills");
        System.out.println("3. Print Invoice by Record ID");
        int sub = ConsoleHelper.readInt("Select option");

        switch (sub) {
            case 1:
                String recId = ConsoleHelper.readString("Enter Completed Service Record ID (e.g. SRV-501)");
                double discount = ConsoleHelper.readDouble("Enter Discount Amount in ₹ (0 for none)");
                String mode = ConsoleHelper.readString("Enter Payment Mode (CASH, CARD, UPI, NET_BANKING)");
                try {
                    Bill bill = billingService.generateBill(recId, discount, mode.toUpperCase());
                    System.out.println(">> SUCCESS: Bill generated successfully!");
                    System.out.println(bill.generatePrintableInvoice());
                } catch (InvalidDataException e) {
                    System.out.println(">> BILLING ERROR: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("\n--- GENERATED TAX INVOICES ---");
                billingService.getAllBills().forEach(System.out::println);
                break;
            case 3:
                String rId = ConsoleHelper.readString("Enter Service Record ID");
                Bill b = billingService.getBillByRecordId(rId);
                if (b != null) {
                    System.out.println(b.generatePrintableInvoice());
                } else {
                    System.out.println(">> No invoice found for Service Record: " + rId);
                }
                break;
            default:
                System.out.println(">> Invalid option.");
        }
    }

    // -------------------------------------------------------------------------
    // FR-06: Operational Reports
    // -------------------------------------------------------------------------
    private static void handleManagerialReports() {
        ConsoleHelper.printSubHeader("OPERATIONAL & WORKLOAD REPORTS (FR-06)");
        System.out.println("1. Vehicle Service History Report");
        System.out.println("2. Vehicle-wise Maintenance Costs Summary");
        System.out.println("3. Service Center Workload & Revenue Metrics");
        int sub = ConsoleHelper.readInt("Select option");

        switch (sub) {
            case 1:
                String veh = ConsoleHelper.readString("Enter Vehicle ID or Registration No (e.g. VEH-101 or TN-33-AX-1001)");
                System.out.println(reportService.generateVehicleServiceHistory(veh));
                break;
            case 2:
                System.out.println(reportService.generateVehicleWiseCostSummary());
                break;
            case 3:
                System.out.println(reportService.generateWorkloadAnalysis());
                break;
            default:
                System.out.println(">> Invalid option.");
        }
    }

    // -------------------------------------------------------------------------
    // FR-07: Data Export for Python Data Science
    // -------------------------------------------------------------------------
    private static void handleExportDataToCsv() {
        ConsoleHelper.printSubHeader("DATA EXPORT TO PYTHON PIPELINE (FR-07)");
        String exportPath = "c:/Activity/4_foundation_of_data_science/data";
        System.out.println("Exporting database entities to CSV at: " + exportPath + " ...");
        boolean success = DatabaseManager.getInstance().exportToCsv(exportPath);
        if (success) {
            System.out.println(">> SUCCESS: Dataset exported successfully!");
            System.out.println("   1. customers.csv");
            System.out.println("   2. vehicles.csv");
            System.out.println("   3. vehicle_maintenance_dataset.csv");
            System.out.println("   Ready for Python Preprocessing & Visualization scripts.");
        } else {
            System.out.println(">> EXPORT FAILED! Check filesystem permissions.");
        }
    }

    // -------------------------------------------------------------------------
    // Automated Demonstration Workflow
    // -------------------------------------------------------------------------
    public static void runAutomatedDemonstration() {
        ConsoleHelper.printHeader("STARTING AUTOMATED END-TO-END DEMO EXECUTION");

        try {
            // Step 1: Register Customer
            System.out.println("\n[DEMO STEP 1] Registering New Customer (FR-01)...");
            Customer c = customerService.registerCustomer("Senthil Velan", "9842199999", "senthil.v@gmail.com", "77 Sakthi Road, Erode");
            System.out.println(">> Created: " + c);

            // Step 2: Register Vehicle
            System.out.println("\n[DEMO STEP 2] Registering New Vehicle (FR-02)...");
            Vehicle v = vehicleService.registerVehicle("TN-33-ZZ-8888", "Tata Safari", VehicleType.SUV, 2023, c.getCustomerId());
            System.out.println(">> Registered: " + v);

            // Step 3: Schedule Appointment
            System.out.println("\n[DEMO STEP 3] Scheduling Service Appointment (FR-03)...");
            LocalDate aptDate = LocalDate.now().plusDays(2);
            ServiceAppointment apt = appointmentService.scheduleAppointment(
                    v.getVehicleId(), aptDate, "10:00 AM - 12:00 PM", "Periodic Maintenance & Brake Inspection");
            System.out.println(">> Booked Appointment: " + apt);

            // Step 4: Maintenance Operations & Parts Allocation
            System.out.println("\n[DEMO STEP 4] Executing Maintenance & Allocating Parts (FR-04)...");
            ServiceRecord rec = maintenanceService.startService(
                    apt.getAppointmentId(), "S. Murugan", "Periodic engine oil change and front brake pads replacement.", 3.0, 500.0);
            System.out.println(">> Service Initiated: " + rec.getRecordId());

            maintenanceService.allocateSparePart(rec.getRecordId(), "PRT-01", 1); // Engine Oil
            maintenanceService.allocateSparePart(rec.getRecordId(), "PRT-03", 1); // Brake Pads
            maintenanceService.completeService(rec.getRecordId());
            System.out.println(">> Service Completed with Parts Allocated!");

            // Step 5: Billing & Invoicing
            System.out.println("\n[DEMO STEP 5] Generating Service Bill (FR-05)...");
            Bill bill = billingService.generateBill(rec.getRecordId(), 150.0, "UPI");
            System.out.println(bill.generatePrintableInvoice());

            // Step 6: Generate Reports
            System.out.println("\n[DEMO STEP 6] Generating Vehicle Service History Report (FR-06)...");
            System.out.println(reportService.generateVehicleServiceHistory(v.getVehicleId()));

            System.out.println("\n[DEMO STEP 7] Generating Service Center Workload Report (FR-06)...");
            System.out.println(reportService.generateWorkloadAnalysis());

            // Step 7: Export to CSV
            System.out.println("\n[DEMO STEP 8] Syncing Data with Python Data Science Pipeline (FR-07)...");
            handleExportDataToCsv();

            System.out.println("\n>> AUTOMATED DEMONSTRATION FINISHED WITH 100% SUCCESS!");

        } catch (Exception e) {
            System.out.println(">> DEMO EXCEPTION: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
