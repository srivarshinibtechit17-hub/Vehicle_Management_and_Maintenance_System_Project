package com.vehicleservice.service;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.model.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reporting and business intelligence service utilizing Java 8+ Stream API
 * for data aggregation, grouping, sorting, and workload analytics (FR-06).
 */
public class ReportService {
    private final DatabaseManager db = DatabaseManager.getInstance();
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Report 1: Service History for a Specific Vehicle.
     */
    public String generateVehicleServiceHistory(String vehicleIdOrRegNo) {
        Vehicle vehicle = db.getVehicle(vehicleIdOrRegNo);
        if (vehicle == null) {
            vehicle = db.getVehicleByRegNo(vehicleIdOrRegNo);
        }
        if (vehicle == null) {
            return ">> ERROR: Vehicle not found for identifier: " + vehicleIdOrRegNo;
        }

        final String targetVehId = vehicle.getVehicleId();
        Customer owner = db.getCustomer(vehicle.getCustomerId());

        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================================\n");
        sb.append(String.format("                      SERVICE HISTORY REPORT: %s (%s)                  \n",
                vehicle.getRegistrationNo(), vehicle.getModel()));
        sb.append(String.format(" Owner: %-25s | Phone: %-15s | Type: %s\n",
                (owner != null ? owner.getFullName() : "N/A"), (owner != null ? owner.getPhone() : "N/A"), vehicle.getVehicleType()));
        sb.append("========================================================================================\n");
        sb.append(String.format("%-10s | %-12s | %-22s | %-15s | %-10s | %-10s\n",
                "Record ID", "Date", "Service Type", "Technician", "Labor (₹)", "Total (₹)"));
        sb.append("----------------------------------------------------------------------------------------\n");

        List<ServiceRecord> history = db.getAllServiceRecords().stream()
                .filter(sr -> sr.getVehicleId().equalsIgnoreCase(targetVehId))
                .sorted(Comparator.comparing(ServiceRecord::getServiceDate))
                .collect(Collectors.toList());

        if (history.isEmpty()) {
            sb.append("  No recorded maintenance history found for this vehicle.\n");
        } else {
            double cumulativeCost = 0.0;
            for (ServiceRecord sr : history) {
                Bill bill = db.getBillByRecordId(sr.getRecordId());
                double total = (bill != null) ? bill.getTotalAmount() : (sr.getLaborCharges() + sr.calculateTotalPartsCost());
                cumulativeCost += total;

                ServiceAppointment apt = db.getAppointment(sr.getAppointmentId());
                String sType = (apt != null) ? apt.getRequestedServiceType() : "General";

                sb.append(String.format("%-10s | %-12s | %-22s | %-15s | %10.2f | %10.2f\n",
                        sr.getRecordId(), sr.getServiceDate().format(DATE_FMT),
                        (sType.length() > 22 ? sType.substring(0, 20) + ".." : sType),
                        sr.getTechnicianName(), sr.getLaborCharges(), total));
            }
            sb.append("----------------------------------------------------------------------------------------\n");
            sb.append(String.format(" CUMULATIVE LIFETIME MAINTENANCE EXPENDITURE: ₹ %.2f\n", cumulativeCost));
        }
        sb.append("========================================================================================\n");
        return sb.toString();
    }

    /**
     * Report 2: Vehicle-wise Maintenance Costs Summary.
     */
    public String generateVehicleWiseCostSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================================================================\n");
        sb.append("                               VEHICLE-WISE MAINTENANCE COST SUMMARY                               \n");
        sb.append("===================================================================================================\n");
        sb.append(String.format("%-10s | %-14s | %-18s | %-12s | %-8s | %-12s | %-12s\n",
                "Veh ID", "Reg Number", "Model", "Type", "Visits", "Parts (₹)", "Total Cost (₹)"));
        sb.append("---------------------------------------------------------------------------------------------------\n");

        // Group bills by vehicleId
        Map<String, List<Bill>> billsByVehicle = db.getAllBills().stream()
                .collect(Collectors.groupingBy(Bill::getVehicleId));

        // Sort vehicles by total cost descending
        List<Map.Entry<String, List<Bill>>> sortedEntries = billsByVehicle.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(
                        e2.getValue().stream().mapToDouble(Bill::getTotalAmount).sum(),
                        e1.getValue().stream().mapToDouble(Bill::getTotalAmount).sum()))
                .collect(Collectors.toList());

        double grandTotal = 0.0;
        for (Map.Entry<String, List<Bill>> entry : sortedEntries) {
            String vehId = entry.getKey();
            Vehicle v = db.getVehicle(vehId);
            String reg = (v != null) ? v.getRegistrationNo() : "N/A";
            String model = (v != null) ? v.getModel() : "N/A";
            String type = (v != null) ? v.getVehicleType().name() : "N/A";

            int visits = entry.getValue().size();
            double partsSum = entry.getValue().stream().mapToDouble(Bill::getPartsCostTotal).sum();
            double totalCost = entry.getValue().stream().mapToDouble(Bill::getTotalAmount).sum();
            grandTotal += totalCost;

            sb.append(String.format("%-10s | %-14s | %-18s | %-12s | %-8d | %12.2f | %14.2f\n",
                    vehId, reg, (model.length() > 18 ? model.substring(0, 16) + ".." : model),
                    type, visits, partsSum, totalCost));
        }
        sb.append("---------------------------------------------------------------------------------------------------\n");
        sb.append(String.format(" TOTAL WORKSHOP BILLED MAINTENANCE COST: ₹ %.2f\n", grandTotal));
        sb.append("===================================================================================================\n");
        return sb.toString();
    }

    /**
     * Report 3: Service Center Workload and Performance Analysis.
     */
    public String generateWorkloadAnalysis() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================================\n");
        sb.append("                         SERVICE CENTER WORKLOAD & PERFORMANCE METRICS                  \n");
        sb.append("========================================================================================\n");

        long totalAppointments = db.getAllAppointments().size();
        long scheduledCount = db.getAllAppointments().stream().filter(a -> a.getStatus() == ServiceStatus.SCHEDULED).count();
        long completedCount = db.getAllAppointments().stream().filter(a -> a.getStatus() == ServiceStatus.COMPLETED).count();

        double totalLaborHours = db.getAllServiceRecords().stream().mapToDouble(ServiceRecord::getLaborHours).sum();
        double totalRevenue = db.getAllBills().stream().mapToDouble(Bill::getTotalAmount).sum();

        sb.append(String.format(" Total Service Appointments Logged : %d\n", totalAppointments));
        sb.append(String.format(" Currently Scheduled / Pending     : %d\n", scheduledCount));
        sb.append(String.format(" Completed Service Jobs            : %d\n", completedCount));
        sb.append(String.format(" Total Technician Labor Hours      : %.2f hours\n", totalLaborHours));
        sb.append(String.format(" Cumulative Center Revenue         : ₹ %.2f\n", totalRevenue));
        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append(" TECHNICIAN WORKLOAD BREAKDOWN:\n");
        sb.append(String.format(" %-20s | %-15s | %-18s | %-15s\n", "Technician Name", "Jobs Handled", "Labor Hours Billed", "Labor Revenue (₹)"));
        sb.append("----------------------------------------------------------------------------------------\n");

        Map<String, List<ServiceRecord>> techRecords = db.getAllServiceRecords().stream()
                .collect(Collectors.groupingBy(ServiceRecord::getTechnicianName));

        for (Map.Entry<String, List<ServiceRecord>> entry : techRecords.entrySet()) {
            String tech = entry.getKey();
            int jobs = entry.getValue().size();
            double hours = entry.getValue().stream().mapToDouble(ServiceRecord::getLaborHours).sum();
            double laborRev = entry.getValue().stream().mapToDouble(ServiceRecord::getLaborCharges).sum();

            sb.append(String.format(" %-20s | %-15d | %18.2f | %17.2f\n", tech, jobs, hours, laborRev));
        }
        sb.append("========================================================================================\n");
        return sb.toString();
    }
}
