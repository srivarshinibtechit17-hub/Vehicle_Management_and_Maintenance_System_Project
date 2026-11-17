-- ============================================================================
-- ADVANCED & ANALYTICAL SQL QUERIES SCRIPT
-- Project: Vehicle Service Management and Maintenance Analysis System
-- Scenario: Scenario 16 (Register Nos: 16, 46, 76, 106)
-- Academic Year: 2026-2027 (ODD) | Course: 25ITT31 Database Management Systems
-- ============================================================================

USE vehicle_service_db;

-- ============================================================================
-- 1. VEHICLE SERVICE HISTORY REPORT (Satisfies FR-06)
-- Retrieves complete chronological maintenance history for a specific vehicle,
-- including work performed, technician assigned, parts used, and bill totals.
-- ============================================================================
SELECT 
    v.vehicle_id,
    v.registration_no,
    v.model,
    c.full_name AS customer_name,
    c.phone,
    sr.record_id,
    DATE(sr.service_date) AS service_date,
    sa.requested_service_type,
    sr.technician_name,
    sr.work_performed,
    sr.labor_charges,
    COALESCE(b.parts_cost_total, 0.00) AS parts_cost,
    COALESCE(b.tax_amount, 0.00) AS tax,
    COALESCE(b.total_amount, 0.00) AS grand_total
FROM vehicles v
JOIN customers c ON v.customer_id = c.customer_id
JOIN service_records sr ON v.vehicle_id = sr.vehicle_id
JOIN service_appointments sa ON sr.appointment_id = sa.appointment_id
LEFT JOIN bills b ON sr.record_id = b.record_id
WHERE v.vehicle_id = 'VEH-101'
ORDER BY sr.service_date ASC;

-- ============================================================================
-- 2. VEHICLE-WISE CUMULATIVE MAINTENANCE COSTS (Satisfies FR-06)
-- Calculates total maintenance expense per vehicle, ranked from highest to lowest.
-- ============================================================================
SELECT 
    v.vehicle_id,
    v.registration_no,
    v.model,
    v.vehicle_type,
    c.full_name AS owner_name,
    COUNT(sr.record_id) AS service_visits_count,
    ROUND(SUM(b.parts_cost_total), 2) AS total_parts_cost,
    ROUND(SUM(b.labor_charges_total), 2) AS total_labor_cost,
    ROUND(SUM(b.total_amount), 2) AS cumulative_maintenance_cost,
    DENSE_RANK() OVER (ORDER BY SUM(b.total_amount) DESC) AS cost_rank
FROM vehicles v
JOIN customers c ON v.customer_id = c.customer_id
JOIN service_records sr ON v.vehicle_id = sr.vehicle_id
JOIN bills b ON sr.record_id = b.record_id
GROUP BY v.vehicle_id, v.registration_no, v.model, v.vehicle_type, c.full_name
ORDER BY cumulative_maintenance_cost DESC;

-- ============================================================================
-- 3. SERVICE CENTER WORKLOAD & BAY OCCUPANCY REPORT (Satisfies FR-06)
-- Aggregates monthly workload, total labor hours billed, and average turn time.
-- ============================================================================
SELECT 
    DATE_FORMAT(sr.service_date, '%Y-%m') AS service_month,
    COUNT(sr.record_id) AS jobs_completed,
    ROUND(SUM(sr.labor_hours), 2) AS total_labor_hours,
    ROUND(AVG(sr.labor_hours), 2) AS avg_hours_per_vehicle,
    ROUND(SUM(b.total_amount), 2) AS monthly_revenue,
    ROUND(AVG(b.total_amount), 2) AS avg_ticket_size
FROM service_records sr
JOIN bills b ON sr.record_id = b.record_id
WHERE sr.service_status = 'COMPLETED'
GROUP BY DATE_FORMAT(sr.service_date, '%Y-%m')
ORDER BY service_month ASC;

-- ============================================================================
-- 4. TECHNICIAN PRODUCTIVITY & REVENUE GENERATION
-- Measures individual mechanic workload, total labor revenue, and efficiency.
-- ============================================================================
SELECT 
    technician_name,
    COUNT(record_id) AS total_vehicles_serviced,
    ROUND(SUM(labor_hours), 2) AS total_labor_hours,
    ROUND(SUM(labor_charges), 2) AS total_labor_revenue_generated,
    ROUND(AVG(labor_charges), 2) AS avg_labor_per_service
FROM service_records
GROUP BY technician_name
ORDER BY total_labor_revenue_generated DESC;

-- ============================================================================
-- 5. MOST FREQUENTLY REPLACED SPARE PARTS & REVENUE CONTRIBUTION
-- Identifies automotive components with the highest turnover and inventory value.
-- ============================================================================
SELECT 
    sp.part_id,
    sp.part_name,
    sp.part_category,
    SUM(pu.quantity_used) AS total_units_consumed,
    ROUND(SUM(pu.total_part_cost), 2) AS total_sales_revenue,
    sp.stock_quantity AS remaining_stock,
    CASE 
        WHEN sp.stock_quantity <= sp.reorder_level THEN 'REORDER REQUIRED'
        ELSE 'SUFFICIENT'
    END AS inventory_status
FROM spare_parts sp
JOIN parts_used pu ON sp.part_id = pu.part_id
GROUP BY sp.part_id, sp.part_name, sp.part_category, sp.stock_quantity, sp.reorder_level
ORDER BY total_units_consumed DESC;

-- ============================================================================
-- 6. CORRELATED NESTED SUBQUERY:
-- Find vehicles whose cumulative maintenance cost is higher than the average
-- maintenance cost of all vehicles of the SAME vehicle type.
-- ============================================================================
SELECT 
    v1.vehicle_id,
    v1.registration_no,
    v1.model,
    v1.vehicle_type,
    SUM(b1.total_amount) AS vehicle_total_cost
FROM vehicles v1
JOIN service_records sr1 ON v1.vehicle_id = sr1.vehicle_id
JOIN bills b1 ON sr1.record_id = b1.record_id
GROUP BY v1.vehicle_id, v1.registration_no, v1.model, v1.vehicle_type
HAVING SUM(b1.total_amount) > (
    -- Subquery: Average cost for the same vehicle type
    SELECT AVG(type_totals.total_spent)
    FROM (
        SELECT v2.vehicle_id, SUM(b2.total_amount) AS total_spent
        FROM vehicles v2
        JOIN service_records sr2 ON v2.vehicle_id = sr2.vehicle_id
        JOIN bills b2 ON sr2.record_id = b2.record_id
        WHERE v2.vehicle_type = v1.vehicle_type
        GROUP BY v2.vehicle_id
    ) AS type_totals
);

-- ============================================================================
-- 7. ADVANCED WINDOW FUNCTION:
-- Calculate running cumulative revenue and percentage contribution of each bill.
-- ============================================================================
SELECT 
    bill_id,
    bill_date,
    total_amount,
    ROUND(SUM(total_amount) OVER (ORDER BY bill_date, bill_id), 2) AS running_cumulative_revenue,
    ROUND((total_amount / SUM(total_amount) OVER ()) * 100, 2) AS pct_of_total_revenue
FROM bills
ORDER BY bill_date ASC;

-- ============================================================================
-- 8. QUERY ON PRE-BUILT DATABASE VIEWS
-- Verify and query the pre-compiled views defined in schema.sql.
-- ============================================================================
SELECT * FROM v_vehicle_maintenance_cost_summary;
SELECT * FROM v_monthly_workload_metrics;
