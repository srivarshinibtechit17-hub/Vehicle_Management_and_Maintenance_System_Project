-- ============================================================================
-- DATABASE DEFINITION SCRIPT (DDL)
-- Project: Vehicle Service Management and Maintenance Analysis System
-- Scenario: Scenario 16 (Register Nos: 16, 46, 76, 106)
-- Academic Year: 2026-2027 (ODD) | Course: 25ITT31 Database Management Systems
-- ============================================================================

CREATE DATABASE IF NOT EXISTS vehicle_service_db;
USE vehicle_service_db;

-- ----------------------------------------------------------------------------
-- 1. Table: customers
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS parts_used;
DROP TABLE IF EXISTS service_records;
DROP TABLE IF EXISTS service_appointments;
DROP TABLE IF EXISTS spare_parts;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS service_audit_log;

CREATE TABLE customers (
    customer_id VARCHAR(15) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    registered_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    CONSTRAINT pk_customers PRIMARY KEY (customer_id),
    CONSTRAINT uq_customers_phone UNIQUE (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 2. Table: vehicles
-- ----------------------------------------------------------------------------
CREATE TABLE vehicles (
    vehicle_id VARCHAR(15) NOT NULL,
    registration_no VARCHAR(20) NOT NULL,
    model VARCHAR(100) NOT NULL,
    vehicle_type VARCHAR(20) NOT NULL,
    manufacturing_year INT NOT NULL,
    customer_id VARCHAR(15) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_vehicles PRIMARY KEY (vehicle_id),
    CONSTRAINT uq_vehicles_reg_no UNIQUE (registration_no),
    CONSTRAINT fk_vehicles_customer FOREIGN KEY (customer_id) 
        REFERENCES customers(customer_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_vehicle_type CHECK (vehicle_type IN ('SEDAN', 'SUV', 'HATCHBACK', 'TRUCK', 'TWO_WHEELER')),
    CONSTRAINT chk_manufacturing_year CHECK (manufacturing_year BETWEEN 1980 AND 2027)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 3. Table: spare_parts
-- ----------------------------------------------------------------------------
CREATE TABLE spare_parts (
    part_id VARCHAR(15) NOT NULL,
    part_name VARCHAR(100) NOT NULL,
    part_category VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    stock_quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 5,
    CONSTRAINT pk_spare_parts PRIMARY KEY (part_id),
    CONSTRAINT chk_part_unit_price CHECK (unit_price >= 0.00),
    CONSTRAINT chk_part_stock_qty CHECK (stock_quantity >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 4. Table: service_appointments
-- ----------------------------------------------------------------------------
CREATE TABLE service_appointments (
    appointment_id VARCHAR(15) NOT NULL,
    vehicle_id VARCHAR(15) NOT NULL,
    customer_id VARCHAR(15) NOT NULL,
    appointment_date DATE NOT NULL,
    time_slot VARCHAR(25) NOT NULL,
    requested_service_type VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_appointments PRIMARY KEY (appointment_id),
    CONSTRAINT fk_apt_vehicle FOREIGN KEY (vehicle_id) 
        REFERENCES vehicles(vehicle_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_apt_customer FOREIGN KEY (customer_id) 
        REFERENCES customers(customer_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_apt_status CHECK (status IN ('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 5. Table: service_records
-- ----------------------------------------------------------------------------
CREATE TABLE service_records (
    record_id VARCHAR(15) NOT NULL,
    appointment_id VARCHAR(15) NOT NULL,
    vehicle_id VARCHAR(15) NOT NULL,
    technician_name VARCHAR(100) NOT NULL,
    work_performed TEXT NOT NULL,
    labor_hours DECIMAL(5,2) NOT NULL DEFAULT 1.00,
    labor_hourly_rate DECIMAL(10,2) NOT NULL DEFAULT 500.00,
    labor_charges DECIMAL(10,2) NOT NULL,
    service_status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS',
    service_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_service_records PRIMARY KEY (record_id),
    CONSTRAINT fk_srv_appointment FOREIGN KEY (appointment_id) 
        REFERENCES service_appointments(appointment_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_srv_vehicle FOREIGN KEY (vehicle_id) 
        REFERENCES vehicles(vehicle_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_labor_hours CHECK (labor_hours >= 0.00),
    CONSTRAINT chk_labor_charges CHECK (labor_charges >= 0.00),
    CONSTRAINT chk_srv_status CHECK (service_status IN ('IN_PROGRESS', 'COMPLETED', 'ON_HOLD'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 6. Table: parts_used (Associative Entity for M:N Service-Parts)
-- ----------------------------------------------------------------------------
CREATE TABLE parts_used (
    usage_id INT AUTO_INCREMENT NOT NULL,
    record_id VARCHAR(15) NOT NULL,
    part_id VARCHAR(15) NOT NULL,
    quantity_used INT NOT NULL DEFAULT 1,
    unit_price_at_service DECIMAL(10,2) NOT NULL,
    total_part_cost DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_parts_used PRIMARY KEY (usage_id),
    CONSTRAINT fk_pu_record FOREIGN KEY (record_id) 
        REFERENCES service_records(record_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT fk_pu_part FOREIGN KEY (part_id) 
        REFERENCES spare_parts(part_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_qty_used CHECK (quantity_used > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 7. Table: bills
-- ----------------------------------------------------------------------------
CREATE TABLE bills (
    bill_id VARCHAR(15) NOT NULL,
    record_id VARCHAR(15) NOT NULL,
    bill_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    parts_cost_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    labor_charges_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    subtotal DECIMAL(10,2) NOT NULL,
    tax_rate DECIMAL(5,2) NOT NULL DEFAULT 18.00,
    tax_amount DECIMAL(10,2) NOT NULL,
    discount_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PAID',
    payment_mode VARCHAR(20) NOT NULL DEFAULT 'CASH',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_bills PRIMARY KEY (bill_id),
    CONSTRAINT uq_bills_record UNIQUE (record_id),
    CONSTRAINT fk_bills_record FOREIGN KEY (record_id) 
        REFERENCES service_records(record_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_pay_status CHECK (payment_status IN ('PAID', 'PENDING', 'REFUNDED')),
    CONSTRAINT chk_pay_mode CHECK (payment_mode IN ('CASH', 'CARD', 'UPI', 'NET_BANKING'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 8. Table: service_audit_log (Audit Trail & Event Log)
-- ----------------------------------------------------------------------------
CREATE TABLE service_audit_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    record_id VARCHAR(15) NOT NULL,
    old_status VARCHAR(20),
    new_status VARCHAR(20),
    changed_by VARCHAR(50) DEFAULT 'SYSTEM',
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- PERFORMANCE INDEXES
-- ============================================================================
CREATE INDEX idx_vehicles_customer ON vehicles(customer_id);
CREATE INDEX idx_appointments_date ON service_appointments(appointment_date, status);
CREATE INDEX idx_service_records_vehicle ON service_records(vehicle_id);
CREATE INDEX idx_service_records_date ON service_records(service_date);
CREATE INDEX idx_parts_used_record ON parts_used(record_id);
CREATE INDEX idx_bills_date ON bills(bill_date);

-- ============================================================================
-- DATABASE VIEWS
-- ============================================================================

-- View 1: Complete Vehicle Service History
CREATE OR REPLACE VIEW v_vehicle_service_history AS
SELECT 
    v.vehicle_id,
    v.registration_no,
    v.model,
    v.vehicle_type,
    c.customer_id,
    c.full_name AS customer_name,
    c.phone,
    sr.record_id,
    sr.service_date,
    sa.requested_service_type,
    sr.technician_name,
    sr.work_performed,
    sr.labor_charges,
    COALESCE(b.parts_cost_total, 0.00) AS parts_cost,
    COALESCE(b.total_amount, 0.00) AS total_bill_amount,
    sr.service_status
FROM vehicles v
JOIN customers c ON v.customer_id = c.customer_id
LEFT JOIN service_records sr ON v.vehicle_id = sr.vehicle_id
LEFT JOIN service_appointments sa ON sr.appointment_id = sa.appointment_id
LEFT JOIN bills b ON sr.record_id = b.record_id;

-- View 2: Vehicle-wise Cumulative Maintenance Cost Summary
CREATE OR REPLACE VIEW v_vehicle_maintenance_cost_summary AS
SELECT 
    v.vehicle_id,
    v.registration_no,
    v.model,
    v.vehicle_type,
    c.full_name AS owner_name,
    COUNT(sr.record_id) AS total_services_count,
    COALESCE(SUM(b.parts_cost_total), 0.00) AS total_parts_expenditure,
    COALESCE(SUM(b.labor_charges_total), 0.00) AS total_labor_expenditure,
    COALESCE(SUM(b.total_amount), 0.00) AS cumulative_maintenance_cost
FROM vehicles v
JOIN customers c ON v.customer_id = c.customer_id
LEFT JOIN service_records sr ON v.vehicle_id = sr.vehicle_id
LEFT JOIN bills b ON sr.record_id = b.record_id
GROUP BY v.vehicle_id, v.registration_no, v.model, v.vehicle_type, c.full_name;

-- View 3: Service Center Monthly Workload & Revenue Metrics
CREATE OR REPLACE VIEW v_monthly_workload_metrics AS
SELECT 
    DATE_FORMAT(sr.service_date, '%Y-%m') AS service_month,
    COUNT(DISTINCT sr.record_id) AS total_services_completed,
    COUNT(DISTINCT sr.vehicle_id) AS unique_vehicles_serviced,
    SUM(sr.labor_hours) AS total_labor_hours,
    SUM(b.total_amount) AS monthly_revenue
FROM service_records sr
JOIN bills b ON sr.record_id = b.record_id
WHERE sr.service_status = 'COMPLETED'
GROUP BY DATE_FORMAT(sr.service_date, '%Y-%m');

-- ============================================================================
-- DATABASE TRIGGERS
-- ============================================================================

DELIMITER $$

-- Trigger 1: Automatically Deduct Spare Part Stock on Usage
CREATE TRIGGER trg_after_part_used_deduct_stock
AFTER INSERT ON parts_used
FOR EACH ROW
BEGIN
    UPDATE spare_parts
    SET stock_quantity = stock_quantity - NEW.quantity_used
    WHERE part_id = NEW.part_id;
END$$

-- Trigger 2: Audit Service Status Changes
CREATE TRIGGER trg_audit_service_status
AFTER UPDATE ON service_records
FOR EACH ROW
BEGIN
    IF OLD.service_status <> NEW.service_status THEN
        INSERT INTO service_audit_log (record_id, old_status, new_status, changed_by)
        VALUES (NEW.record_id, OLD.service_status, NEW.service_status, USER());
    END IF;
END$$

DELIMITER ;
