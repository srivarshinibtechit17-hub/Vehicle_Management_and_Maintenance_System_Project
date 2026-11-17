-- ============================================================================
-- SAMPLE DATA INSERTION SCRIPT (DML)
-- Project: Vehicle Service Management and Maintenance Analysis System
-- Scenario: Scenario 16 (Register Nos: 16, 46, 76, 106)
-- Academic Year: 2026-2027 (ODD) | Course: 25ITT31 Database Management Systems
-- ============================================================================

USE vehicle_service_db;

-- ----------------------------------------------------------------------------
-- 1. Insert Customers (10 Customers)
-- ----------------------------------------------------------------------------
INSERT INTO customers (customer_id, full_name, phone, email, address, registered_date) VALUES
('CUST-1001', 'Arun Kumar', '9842100001', 'arun.kumar@gmail.com', '12 Perundurai Road, Erode', '2026-01-10'),
('CUST-1002', 'Priya Dharshini', '9842100002', 'priya.d@yahoo.com', '45 Brough Road, Erode', '2026-01-15'),
('CUST-1003', 'Karthik Raja', '9842100003', 'karthik.raja@outlook.com', '88 Gandhipuram, Coimbatore', '2026-02-01'),
('CUST-1004', 'Sneha Lakshmi', '9842100004', 'sneha.l@gmail.com', '104 Mettur Road, Erode', '2026-02-12'),
('CUST-1005', 'Venkatesh S', '9842100005', 'venkat.s@gmail.com', '23 EVN Road, Erode', '2026-03-05'),
('CUST-1006', 'Divya Bharathi', '9842100006', 'divya.b@hotmail.com', '78 Thindal, Erode', '2026-03-20'),
('CUST-1007', 'Manoj Prabhakar', '9842100007', 'manoj.p@gmail.com', '56 Collectorate Post, Salem', '2026-04-10'),
('CUST-1008', 'Kavitha R', '9842100008', 'kavitha.r@gmail.com', '91 Kollampalayam, Erode', '2026-05-02'),
('CUST-1009', 'Suresh Babu', '9842100009', 'suresh.babu@gmail.com', '14 Chithode, Erode', '2026-06-15'),
('CUST-1010', 'Deepa Shankar', '9842100010', 'deepa.s@gmail.com', '33 Nasiyanur Road, Erode', '2026-07-01');

-- ----------------------------------------------------------------------------
-- 2. Insert Vehicles (15 Vehicles across different categories)
-- ----------------------------------------------------------------------------
INSERT INTO vehicles (vehicle_id, registration_no, model, vehicle_type, manufacturing_year, customer_id) VALUES
('VEH-101', 'TN-33-AX-1001', 'Hyundai Creta', 'SUV', 2022, 'CUST-1001'),
('VEH-102', 'TN-33-BF-2045', 'Maruti Suzuki Swift', 'HATCHBACK', 2021, 'CUST-1002'),
('VEH-103', 'TN-38-CC-3344', 'Honda City', 'SEDAN', 2023, 'CUST-1003'),
('VEH-104', 'TN-33-DZ-9080', 'Tata Nexon EV', 'SUV', 2024, 'CUST-1004'),
('VEH-105', 'TN-33-ER-4455', 'Royal Enfield Classic 350', 'TWO_WHEELER', 2022, 'CUST-1005'),
('VEH-106', 'TN-33-GH-5566', 'Toyota Innova Crysta', 'SUV', 2020, 'CUST-1001'),
('VEH-107', 'TN-33-JK-7788', 'Tata Ace Gold', 'TRUCK', 2019, 'CUST-1007'),
('VEH-108', 'TN-30-LM-9900', 'Hyundai i20', 'HATCHBACK', 2021, 'CUST-1006'),
('VEH-109', 'TN-33-NP-1122', 'Mahindra Scorpio-N', 'SUV', 2023, 'CUST-1008'),
('VEH-110', 'TN-33-RS-3344', 'Yamaha MT-15', 'TWO_WHEELER', 2023, 'CUST-1005'),
('VEH-111', 'TN-38-TV-5566', 'Skoda Slavia', 'SEDAN', 2022, 'CUST-1003'),
('VEH-112', 'TN-33-WX-7788', 'Ashok Leyland Dost', 'TRUCK', 2021, 'CUST-1007'),
('VEH-113', 'TN-33-YZ-9900', 'Kia Seltos', 'SUV', 2022, 'CUST-1009'),
('VEH-114', 'TN-33-AA-1234', 'Honda Activa 6G', 'TWO_WHEELER', 2024, 'CUST-1010'),
('VEH-115', 'TN-33-BC-5678', 'Maruti Baleno', 'HATCHBACK', 2020, 'CUST-1002');

-- ----------------------------------------------------------------------------
-- 3. Insert Spare Parts Inventory (15 Parts)
-- ----------------------------------------------------------------------------
INSERT INTO spare_parts (part_id, part_name, part_category, unit_price, stock_quantity, reorder_level) VALUES
('PRT-01', 'Synthetic Engine Oil (3.5L)', 'Fluids', 1850.00, 45, 10),
('PRT-02', 'Oil Filter Element', 'Filters', 350.00, 60, 15),
('PRT-03', 'Front Brake Pads Set', 'Brakes', 1600.00, 25, 8),
('PRT-04', 'Rear Brake Shoes', 'Brakes', 1100.00, 20, 5),
('PRT-05', 'Engine Air Filter', 'Filters', 480.00, 35, 10),
('PRT-06', 'Cabin AC Pollen Filter', 'Filters', 550.00, 30, 8),
('PRT-07', 'Spark Plugs (Set of 4)', 'Electrical', 920.00, 28, 6),
('PRT-08', 'Coolant Fluid (1L)', 'Fluids', 380.00, 50, 12),
('PRT-09', 'Wiper Blades Pair', 'Accessories', 650.00, 40, 10),
('PRT-10', 'Clutch Plate Assembly', 'Transmission', 4200.00, 12, 4),
('PRT-11', 'Front Shock Absorber', 'Suspension', 3100.00, 10, 3),
('PRT-12', 'Battery 12V 45Ah', 'Electrical', 4800.00, 15, 4),
('PRT-13', 'Brake Fluid DOT 4 (500ml)', 'Fluids', 280.00, 40, 10),
('PRT-14', 'Headlamp LED Bulb', 'Electrical', 750.00, 30, 8),
('PRT-15', 'Drive Belt', 'Engine', 850.00, 18, 5);

-- ----------------------------------------------------------------------------
-- 4. Insert Service Appointments (15 Appointments)
-- ----------------------------------------------------------------------------
INSERT INTO service_appointments (appointment_id, vehicle_id, customer_id, appointment_date, time_slot, requested_service_type, status) VALUES
('APT-301', 'VEH-101', 'CUST-1001', '2026-08-05', '09:00 AM - 11:00 AM', 'Periodic Maintenance', 'COMPLETED'),
('APT-302', 'VEH-102', 'CUST-1002', '2026-08-08', '11:00 AM - 01:00 PM', 'Brake Repair & Overhaul', 'COMPLETED'),
('APT-303', 'VEH-103', 'CUST-1003', '2026-08-12', '02:00 PM - 04:00 PM', 'Periodic Maintenance', 'COMPLETED'),
('APT-304', 'VEH-104', 'CUST-1004', '2026-08-19', '09:00 AM - 11:00 AM', 'AC System Overhaul', 'COMPLETED'),
('APT-305', 'VEH-105', 'CUST-1005', '2026-08-25', '11:00 AM - 01:00 PM', 'General Service', 'COMPLETED'),
('APT-306', 'VEH-106', 'CUST-1001', '2026-09-02', '09:00 AM - 11:00 AM', 'Clutch Replacement', 'COMPLETED'),
('APT-307', 'VEH-107', 'CUST-1007', '2026-09-05', '02:00 PM - 04:00 PM', 'Major Overhaul', 'COMPLETED'),
('APT-308', 'VEH-108', 'CUST-1006', '2026-09-12', '10:00 AM - 12:00 PM', 'Periodic Maintenance', 'COMPLETED'),
('APT-309', 'VEH-109', 'CUST-1008', '2026-09-18', '02:00 PM - 04:00 PM', 'Suspension Repair', 'COMPLETED'),
('APT-310', 'VEH-110', 'CUST-1005', '2026-09-24', '11:00 AM - 01:00 PM', 'Electrical Inspection', 'COMPLETED'),
('APT-311', 'VEH-111', 'CUST-1003', '2026-10-02', '09:00 AM - 11:00 AM', 'Periodic Maintenance', 'COMPLETED'),
('APT-312', 'VEH-112', 'CUST-1007', '2026-10-10', '02:00 PM - 04:00 PM', 'Brake & Wheel Hub Service', 'COMPLETED'),
('APT-313', 'VEH-113', 'CUST-1009', '2026-10-18', '10:00 AM - 12:00 PM', 'Oil Change & Filter Service', 'COMPLETED'),
('APT-314', 'VEH-101', 'CUST-1001', '2026-11-05', '09:00 AM - 11:00 AM', 'Pre-Trip Inspection & Tuneup', 'COMPLETED'),
('APT-315', 'VEH-115', 'CUST-1002', '2026-11-20', '02:00 PM - 04:00 PM', 'Periodic Maintenance', 'SCHEDULED');

-- ----------------------------------------------------------------------------
-- 5. Insert Service Records (14 Completed Services)
-- ----------------------------------------------------------------------------
INSERT INTO service_records (record_id, appointment_id, vehicle_id, technician_name, work_performed, labor_hours, labor_hourly_rate, labor_charges, service_status, service_date) VALUES
('SRV-501', 'APT-301', 'VEH-101', 'S. Murugan', 'Engine oil flushed, oil filter and air filter renewed, 40-point safety check done.', 2.50, 500.00, 1250.00, 'COMPLETED', '2026-08-05 14:30:00'),
('SRV-502', 'APT-302', 'VEH-102', 'R. Saravanan', 'Front brake pads replaced, brake caliper cleaned, brake fluid bled and replenished.', 2.00, 500.00, 1000.00, 'COMPLETED', '2026-08-08 16:00:00'),
('SRV-503', 'APT-303', 'VEH-103', 'K. Prakash', 'Regular 20,000 KM service, oil change, spark plug replacement, wheel balancing.', 3.00, 500.00, 1500.00, 'COMPLETED', '2026-08-12 17:30:00'),
('SRV-504', 'APT-304', 'VEH-104', 'M. Vijay', 'AC cooling coil inspected, cabin pollen filter replaced, refrigerant gas refilled.', 2.00, 600.00, 1200.00, 'COMPLETED', '2026-08-19 12:45:00'),
('SRV-505', 'APT-305', 'VEH-105', 'S. Murugan', 'Engine tuning, drive chain lubricated, spark plug cleaned, oil change.', 1.50, 400.00, 600.00, 'COMPLETED', '2026-08-25 13:15:00'),
('SRV-506', 'APT-306', 'VEH-106', 'R. Saravanan', 'Complete clutch plate & pressure plate replacement, flywheel skimmed, test drive.', 5.00, 550.00, 2750.00, 'COMPLETED', '2026-09-02 18:00:00'),
('SRV-507', 'APT-307', 'VEH-107', 'T. Dinesh', 'Full commercial truck inspection, drive belt renewed, oil and coolant change.', 4.50, 500.00, 2250.00, 'COMPLETED', '2026-09-05 17:15:00'),
('SRV-508', 'APT-308', 'VEH-108', 'K. Prakash', 'Periodic service, new wiper blades, coolant flush, oil filter change.', 2.50, 500.00, 1250.00, 'COMPLETED', '2026-09-12 14:00:00'),
('SRV-509', 'APT-309', 'VEH-109', 'R. Saravanan', 'Front suspension shock absorber replacement, strut mounts renewed, alignment.', 4.00, 550.00, 2200.00, 'COMPLETED', '2026-09-18 16:45:00'),
('SRV-510', 'APT-310', 'VEH-110', 'M. Vijay', 'Battery testing and replacement, new LED headlamp bulb installed, wiring audit.', 1.50, 400.00, 600.00, 'COMPLETED', '2026-09-24 13:00:00'),
('SRV-511', 'APT-311', 'VEH-111', 'S. Murugan', 'Periodic synthetic oil renewal, cabin filter, brake fluid bleeding.', 3.00, 500.00, 1500.00, 'COMPLETED', '2026-10-02 15:30:00'),
('SRV-512', 'APT-312', 'VEH-112', 'T. Dinesh', 'Rear brake shoes replaced, wheel bearing greased, brake drum polished.', 3.50, 500.00, 1750.00, 'COMPLETED', '2026-10-10 18:00:00'),
('SRV-513', 'APT-313', 'VEH-113', 'K. Prakash', 'Engine oil renewed, oil filter replaced, 30-point monsoon checkup.', 2.00, 500.00, 1000.00, 'COMPLETED', '2026-10-18 13:30:00'),
('SRV-514', 'APT-314', 'VEH-101', 'S. Murugan', 'Brake pad inspection, wiper pair renewal, battery load test, coolant top-up.', 1.80, 500.00, 900.00, 'COMPLETED', '2026-11-05 12:00:00');

-- ----------------------------------------------------------------------------
-- 6. Insert Parts Used (Junction records)
-- ----------------------------------------------------------------------------
INSERT INTO parts_used (record_id, part_id, quantity_used, unit_price_at_service, total_part_cost) VALUES
('SRV-501', 'PRT-01', 1, 1850.00, 1850.00),
('SRV-501', 'PRT-02', 1, 350.00, 350.00),
('SRV-501', 'PRT-05', 1, 480.00, 480.00),

('SRV-502', 'PRT-03', 1, 1600.00, 1600.00),
('SRV-502', 'PRT-13', 1, 280.00, 280.00),

('SRV-503', 'PRT-01', 1, 1850.00, 1850.00),
('SRV-503', 'PRT-02', 1, 350.00, 350.00),
('SRV-503', 'PRT-07', 1, 920.00, 920.00),

('SRV-504', 'PRT-06', 1, 550.00, 550.00),

('SRV-505', 'PRT-01', 1, 1200.00, 1200.00), -- Bike grade oil pack

('SRV-506', 'PRT-10', 1, 4200.00, 4200.00),
('SRV-506', 'PRT-08', 1, 380.00, 380.00),

('SRV-507', 'PRT-01', 2, 1850.00, 3700.00),
('SRV-507', 'PRT-15', 1, 850.00, 850.00),

('SRV-508', 'PRT-01', 1, 1850.00, 1850.00),
('SRV-508', 'PRT-02', 1, 350.00, 350.00),
('SRV-508', 'PRT-09', 1, 650.00, 650.00),

('SRV-509', 'PRT-11', 2, 3100.00, 6200.00),

('SRV-510', 'PRT-12', 1, 4800.00, 4800.00),
('SRV-510', 'PRT-14', 1, 750.00, 750.00),

('SRV-511', 'PRT-01', 1, 1850.00, 1850.00),
('SRV-511', 'PRT-06', 1, 550.00, 550.00),
('SRV-511', 'PRT-13', 1, 280.00, 280.00),

('SRV-512', 'PRT-04', 2, 1100.00, 2200.00),

('SRV-513', 'PRT-01', 1, 1850.00, 1850.00),
('SRV-513', 'PRT-02', 1, 350.00, 350.00),

('SRV-514', 'PRT-09', 1, 650.00, 650.00),
('SRV-514', 'PRT-08', 1, 380.00, 380.00);

-- ----------------------------------------------------------------------------
-- 7. Insert Bills (14 Invoices)
-- ----------------------------------------------------------------------------
-- Formulas:
-- subtotal = parts_cost_total + labor_charges_total
-- tax_amount = (subtotal - discount_amount) * 0.18
-- total_amount = (subtotal - discount_amount) + tax_amount

INSERT INTO bills (bill_id, record_id, bill_date, parts_cost_total, labor_charges_total, subtotal, tax_rate, tax_amount, discount_amount, total_amount, payment_status, payment_mode) VALUES
('BIL-801', 'SRV-501', '2026-08-05', 2680.00, 1250.00, 3930.00, 18.00, 707.40, 0.00, 4637.40, 'PAID', 'UPI'),
('BIL-802', 'SRV-502', '2026-08-08', 1880.00, 1000.00, 2880.00, 18.00, 518.40, 100.00, 3298.40, 'PAID', 'CARD'),
('BIL-803', 'SRV-503', '2026-08-12', 3120.00, 1500.00, 4620.00, 18.00, 831.60, 200.00, 5251.60, 'PAID', 'CARD'),
('BIL-804', 'SRV-504', '2026-08-19', 550.00, 1200.00, 1750.00, 18.00, 315.00, 0.00, 2065.00, 'PAID', 'UPI'),
('BIL-805', 'SRV-505', '2026-08-25', 1200.00, 600.00, 1800.00, 18.00, 324.00, 50.00, 2074.00, 'PAID', 'CASH'),
('BIL-806', 'SRV-506', '2026-09-02', 4580.00, 2750.00, 7330.00, 18.00, 1319.40, 300.00, 8349.40, 'PAID', 'NET_BANKING'),
('BIL-807', 'SRV-507', '2026-09-05', 4550.00, 2250.00, 6800.00, 18.00, 1224.00, 0.00, 8024.00, 'PAID', 'CARD'),
('BIL-808', 'SRV-508', '2026-09-12', 2850.00, 1250.00, 4100.00, 18.00, 738.00, 150.00, 4688.00, 'PAID', 'UPI'),
('BIL-809', 'SRV-509', '2026-09-18', 6200.00, 2200.00, 8400.00, 18.00, 1512.00, 400.00, 9512.00, 'PAID', 'CARD'),
('BIL-810', 'SRV-510', '2026-09-24', 5550.00, 600.00, 6150.00, 18.00, 1107.00, 100.00, 7157.00, 'PAID', 'UPI'),
('BIL-811', 'SRV-511', '2026-10-02', 2680.00, 1500.00, 4180.00, 18.00, 752.40, 0.00, 4932.40, 'PAID', 'UPI'),
('BIL-812', 'SRV-512', '2026-10-10', 2200.00, 1750.00, 3950.00, 18.00, 711.00, 150.00, 4511.00, 'PAID', 'CASH'),
('BIL-813', 'SRV-513', '2026-10-18', 2200.00, 1000.00, 3200.00, 18.00, 576.00, 0.00, 3776.00, 'PAID', 'CARD'),
('BIL-814', 'SRV-514', '2026-11-05', 1030.00, 900.00, 1930.00, 18.00, 347.40, 50.00, 2227.40, 'PAID', 'UPI');

COMMIT;
