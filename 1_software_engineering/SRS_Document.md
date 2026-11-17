# Software Requirements Specification (SRS)
## Vehicle Service Management and Maintenance Analysis System
**Document Version:** 1.0.0  
**Standard:** IEEE Std 830-1998  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  
**Academic Year:** 2026-2027 (ODD) | **Department:** Information Technology  

---

## 1. Introduction

### 1.1 Purpose
The purpose of this document is to provide a complete, formal Software Requirements Specification (SRS) for the **Vehicle Service Management and Maintenance Analysis System**. This document defines the functional and non-functional requirements, external interfaces, system domain constraints, and verification criteria for developers, database administrators, testers, and stakeholders.

### 1.2 Scope of the System
The system is an enterprise-grade automobile workshop and service center application that manages end-to-end service lifecycles. It eliminates traditional paper-based workflows and scheduling chaos by automating:
- Customer and vehicle registration and profile lifecycle.
- Service appointment booking and slot availability verification.
- Recording detailed maintenance activities, labor charges, and spare parts consumption.
- Automated billing calculation incorporating labor, parts, taxes, and discounts.
- Comprehensive operational reports for service history, vehicle-wise maintenance expenditures, and workshop bay workload.
- Analytical data extraction and visualization using Python to uncover trends in failure modes, service demands, and revenue streams.

### 1.3 Definitions, Acronyms, and Abbreviations
- **SRS:** Software Requirements Specification
- **DBMS:** Database Management System
- **RDBMS:** Relational Database Management System
- **PK / FK:** Primary Key / Foreign Key
- **EDA:** Exploratory Data Analysis
- **OOP:** Object-Oriented Programming
- **DAO:** Data Access Object
- **VIN:** Vehicle Identification Number / Registration Number
- **BVA:** Boundary Value Analysis
- **ECP:** Equivalence Class Partitioning

### 1.4 References
- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications.
- VCET IT Department R2025 Syllabus (25ITT32, 25ITT31, 25ITC31).
- Database System Concepts, 7th Edition (Silberschatz, Korth, Sudarshan).
- Java: The Complete Reference, 13th Edition (Herbert Schildt).

---

## 2. Overall Description

### 2.1 Product Perspective
The Vehicle Service Management and Maintenance Analysis System operates as a modular, 3-tier enterprise architecture:
1. **User Presentation & CLI Tier:** Java Console / CLI interface facilitating role-based user interactions.
2. **Business Logic & Persistence Tier:** Java Core Application implementing domain models, validation, appointment collision detection, billing calculations, and DAO database interfaces.
3. **Data & Analytics Tier:** MySQL relational database managing persistent tables, complemented by Python Data Science pipelines for statistical analysis and visualization.

```
+-----------------------------------------------------------+
|              Presentation Layer (Java CLI / UI)           |
+-----------------------------------------------------------+
                             |
                             v
+-----------------------------------------------------------+
|   Business Logic Layer (Services, Validation, Multithread)|
+-----------------------------------------------------------+
            |                                   |
            v                                   v
+-----------------------+           +-----------------------+
|  Data Access (DAO)    |           |  Python Analytics &   |
|  & Persistence Engine |           |  Data Preprocessing   |
+-----------------------+           +-----------------------+
            |                                   |
            +-----------------+-----------------+
                              |
                              v
             +----------------------------------+
             |    Relational Database (MySQL)   |
             +----------------------------------+
```

### 2.2 User Classes and Characteristics
- **Service Advisor:** Registers customers and vehicles, books service appointments, and issues customer estimates.
- **Service Technician / Mechanic:** Records actual maintenance operations, specifies spare parts installed, logs labor hours, and updates job completion status.
- **Billing Manager / Cashier:** Validates completed service records, verifies spare parts tally, applies tax/discounts, and generates final itemized tax bills.
- **Service Center General Manager:** Analyzes operational reports, technician workload, high-cost maintenance vehicles, and monthly revenue.
- **Data Analyst:** Extracts service datasets, runs Python preprocessing pipelines, and prepares visual intelligence reports.

### 2.3 Operating Environment
- **Operating System:** Windows 10/11, Linux (Ubuntu 22.04+), macOS.
- **Java Platform:** Java SE Development Kit (JDK) 17 or higher.
- **DBMS:** MySQL Community Server 8.0 / MySQL Shell / Workbench.
- **Python Environment:** Python 3.10+ with Pandas, NumPy, Matplotlib, and Seaborn.

### 2.4 Design and Implementation Constraints
- The backend core must be programmed in Java adhering to strict Object-Oriented Principles (Encapsulation, Polymorphism, Inheritance, Interfaces).
- Relational schema must be normalized to at least Third Normal Form (3NF) to eliminate insertion, update, and deletion anomalies.
- All database queries must enforce foreign key integrity and use parameterized statements to prevent SQL injection.
- Python data visualization scripts must handle missing data and outliers gracefully.

---

## 3. Specific Requirements

### 3.1 External Interface Requirements
- **User Interfaces:** Interactive text-based menu-driven console with numbered options, input prompts, clear tabular outputs, and validation feedback.
- **Software Interfaces:**
  - JDBC (Java Database Connectivity) API to connect Java service classes to MySQL.
  - MySQL Connector / Python (`mysql-connector-python`) and `pandas.read_sql` or CSV pipelines for the Data Science engine.
- **Hardware Interfaces:** Standard PC with minimum 4 GB RAM and 500 MB free storage.

---

### 3.2 Functional Requirements

#### 3.2.1 FR-01: Customer Profile Management
- **Description:** Maintain accurate customer records including ID, full name, phone number, email address, and physical address.
- **Inputs:** Customer Name (String), Phone Number (10 digits), Email (valid email format), Address (String).
- **Processing:** Validate format; generate auto-incremented or formatted Customer ID (e.g., `CUST-1001`); persist in `customers` table.
- **Outputs:** Confirmation message with assigned Customer ID.
- **Exceptions:** Invalid phone format (not 10 digits) raises `InvalidDataException`. Duplicate customer record warnings.

#### 3.2.2 FR-02: Vehicle Registration and Asset Tracking
- **Description:** Maintain vehicle details linked to registered customers.
- **Inputs:** Vehicle Registration Number (Unique alphanumeric, e.g., `TN-33-AB-1234`), Make & Model (e.g., `Hyundai Creta`), Vehicle Type (`SEDAN`, `SUV`, `HATCHBACK`, `TRUCK`, `TWO_WHEELER`), Manufacturing Year, and Owner Customer ID.
- **Processing:** Check if Customer ID exists; verify uniqueness of Registration Number; link vehicle to customer.
- **Outputs:** Vehicle successfully registered and linked to customer profile.
- **Exceptions:** If Customer ID does not exist, throw `CustomerNotFoundException`. If registration number is duplicate, throw `DuplicateVehicleException`.

#### 3.2.3 FR-03: Service Appointment Scheduling
- **Description:** Allow scheduling of service requests, tracking service date, requested service type, and designated service bay.
- **Inputs:** Vehicle ID, Appointment Date, Time Slot, Service Type (e.g., `Periodic Maintenance`, `Brake Overhaul`, `Oil Change`, `Engine Diagnostic`, `AC Repair`).
- **Processing:** Validate date (must not be in the past); check bay capacity for selected date/slot to prevent double-booking.
- **Outputs:** Confirmed appointment booking with Status = `SCHEDULED`.
- **Exceptions:** Slot collision throws `AppointmentConflictException`. Invalid date throws `InvalidDataException`.

#### 3.2.4 FR-04: Maintenance Activity Recording
- **Description:** Maintain service records including detailed work performed, spare parts utilized, labor charges, mechanic assignment, and service status.
- **Inputs:** Appointment ID, Mechanic Name/ID, Work Description, Spare Parts IDs and Quantities, Labor Hours and Hourly Labor Rate.
- **Processing:** Deduct used spare parts from inventory; calculate total labor charge ($\text{Labor Hours} \times \text{Hourly Rate}$); record state transitions (`SCHEDULED` $\rightarrow$ `IN_PROGRESS` $\rightarrow$ `COMPLETED`).
- **Outputs:** Stored `ServiceRecord` entity with active status and itemized parts ledger.
- **Exceptions:** Insufficient spare parts stock throws `InsufficientStockException`.

#### 3.2.5 FR-05: Service Billing and Invoicing
- **Description:** Automatically calculate service bills based on labor charges, spare part costs, applicable taxes (e.g., 18% GST), and discount schemes.
- **Inputs:** Service Record ID, Discount Percentage (if applicable), Payment Mode (Cash, Card, UPI).
- **Processing:**
  $$\text{Parts Cost} = \sum (\text{Part Quantity} \times \text{Unit Price})$$
  $$\text{Subtotal} = \text{Parts Cost} + \text{Labor Charges}$$
  $$\text{Tax (GST 18\%)} = 0.18 \times \text{Subtotal}$$
  $$\text{Total Bill} = (\text{Subtotal} + \text{Tax}) - \text{Discount}$$
- **Outputs:** Formal itemized Bill with Bill ID, breakdown of parts, labor, tax, total payable, and payment status `PAID`.
- **Exceptions:** Attempting to bill an incomplete service record throws `IllegalStateException`.

#### 3.2.6 FR-06: Operational and Workload Reporting
- **Description:** Generate managerial reports including:
  1. Complete Service History for any given vehicle (chronological list of repairs, parts replaced, and costs).
  2. Vehicle-wise cumulative maintenance costs.
  3. Service Center Workload (count of services handled per month, bay utilization, mechanic performance).
- **Inputs:** Vehicle ID or Report Timeframe (Month/Year).
- **Processing:** Aggregate relational data using SQL Joins / Java Stream API grouping and filtering.
- **Outputs:** Formatted tabular managerial reports.

#### 3.2.7 FR-07: Data Extraction, Preprocessing & Visualization (Python)
- **Description:** Retrieve database records using Python, clean and preprocess data, and generate informative charts.
- **Inputs:** Connection parameters to MySQL database or exported dataset.
- **Processing:** Handle missing values, remove duplicates, filter outliers via IQR/z-score, compute aggregates and correlations.
- **Outputs:** Publication-quality graphical charts (Bar charts, Box plots, Time-series trends, Scatter plots, Heatmaps).

---

## 4. Non-Functional Requirements

### 4.1 Performance Requirements
- Service history lookup for any vehicle must execute in $< 300\text{ ms}$.
- Billing calculation must occur instantaneously ($< 50\text{ ms}$).
- Python data preprocessing pipeline must handle $> 50,000$ service records within 5 seconds.

### 4.2 Security Requirements
- Role-based separation between technicians (record work only) and billing cashiers (generate invoices and accept payments).
- Sanitized database interactions through Prepared Statements to eliminate SQL injection attacks.

### 4.3 Software Quality Attributes
- **Modularity:** High cohesion within modules (Customer, Vehicle, Billing, Reporting) and loose coupling through interfaces.
- **Reusability:** Reusable DAO classes and generic collections.
- **Robustness:** No unhandled exceptions; informative user error prompts.
- **Traceability:** Unique identifiers for all transactions (Customer ID, Vehicle ID, Appointment ID, Record ID, Bill ID).
