# Testing Techniques and Test Cases Documentation
## Vehicle Service Management and Maintenance Analysis System
**Department of Information Technology** | **Academic Year:** 2026-2027 (ODD)  
**Course:** Software Engineering / Project-Based Assessment  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Testing Philosophy & Objectives
The quality assurance strategy for the Vehicle Service Management and Maintenance Analysis System employs a multi-tiered verification and validation framework to guarantee operational reliability, numerical precision in billing, data integrity across database transactions, and analytical robustness.

### 1.1 Testing Levels
1. **Unit Testing:** Focuses on individual Java methods, entity validations, custom exception throws, and mathematical calculations (labor and spare part bill summations).
2. **Integration Testing:** Verifies data flow between the Java application layer, the DAO layer, and the MySQL database engine via JDBC.
3. **System Testing:** Validates end-to-end user workflows (e.g., registering customer $\rightarrow$ linking vehicle $\rightarrow$ scheduling appointment $\rightarrow$ recording maintenance $\rightarrow$ generating bill $\rightarrow$ updating inventory $\rightarrow$ Python report generation).
4. **Acceptance Testing:** Confirms that all 7 Functional Requirements specified in the project scenario are fully satisfied.

---

## 2. Testing Techniques Applied

### 2.1 Black-Box Testing Techniques

#### A. Equivalence Class Partitioning (ECP)
Divides the input domain of the software into distinct classes from which test cases are derived:
- **Phone Number:**
  - *Valid Class:* Exact 10 numeric digits (e.g., `9876543210`).
  - *Invalid Class 1:* Less than 10 digits (e.g., `98765`).
  - *Invalid Class 2:* More than 10 digits (e.g., `987654321099`).
  - *Invalid Class 3:* Non-numeric characters (e.g., `98765ABCD0`).
- **Vehicle Manufacturing Year:**
  - *Valid Class:* Between 1980 and current calendar year (e.g., 2022).
  - *Invalid Class:* Prior to 1980 or future year $> \text{current year} + 1$.
- **Appointment Slot Allocation:**
  - *Valid Class:* Unbooked time slot on a working business day.
  - *Invalid Class:* Double-booked time slot for the same bay/date.

#### B. Boundary Value Analysis (BVA)
Tests input values at the extreme boundaries:
- **Discount Percentage:** Boundaries: `0.0%`, `0.01%`, `50.0%`, `99.99%`, `100.0%`. Values $<0$ or $>100$ rejected.
- **Labor Hours:** Minimum boundary: `0.5` hours. Upper operational boundary: `24.0` hours per individual task.
- **Spare Part Quantity:** Minimum: `1` unit. Upper bound: current stock quantity $Q_{\text{stock}}$.

---

### 2.2 White-Box Testing Techniques

#### Basis Path Testing & Cyclomatic Complexity for Billing Logic
Consider the billing calculation logic:
```java
public double calculateTotalBill(double labor, double parts, double discountPct, double taxRate) {
    if (labor < 0 || parts < 0) { // Decision 1
        throw new InvalidDataException("Charges cannot be negative");
    }
    double subtotal = labor + parts;
    if (discountPct > 50.0) {     // Decision 2
        discountPct = 50.0; // cap maximum promotional discount
    }
    double discountAmount = subtotal * (discountPct / 100.0);
    double taxable = subtotal - discountAmount;
    double taxAmount = taxable * taxRate;
    return taxable + taxAmount;
}
```
- **Number of Decision Points ($P$):** 2
- **Cyclomatic Complexity $V(G) = P + 1 = 2 + 1 = 3$**
- **Independent Basis Paths:**
  - **Path 1:** Negative charges $\rightarrow$ Throws `InvalidDataException`.
  - **Path 2:** Valid charges, discount $\le 50.0\% \rightarrow$ Standard discount and tax applied.
  - **Path 3:** Valid charges, discount $> 50.0\% \rightarrow$ Discount capped at 50% and tax applied.

---

## 3. Comprehensive Test Suite & Execution Results

| Test ID | Module / Requirement | Test Scenario | Preconditions | Input Data | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|---|---|
| **TC-CUST-01** | FR-01 (Customer) | Register valid new customer | System running, DB online | Name: "Ramesh Kumar", Phone: "9842154321", Email: "ramesh@gmail.com", Address: "Erode" | Customer record created, assigned ID `CUST-1001` | Customer `CUST-1001` created successfully | **PASS** |
| **TC-CUST-02** | FR-01 (Customer) | Register customer with invalid phone number | System running | Name: "Anita Devi", Phone: "98421ABC", Email: "anita@gmail.com" | Rejects input; throws `InvalidDataException` | Rejected with message: "Phone must be 10 digits" | **PASS** |
| **TC-VEH-01** | FR-02 (Vehicle) | Register valid vehicle linked to existing customer | `CUST-1001` exists | RegNo: "TN-33-AX-5544", Model: "Hyundai Creta", Type: "SUV", Year: 2023, Owner: "CUST-1001" | Vehicle registered and mapped to `CUST-1001` | Vehicle `VEH-101` registered and mapped | **PASS** |
| **TC-VEH-02** | FR-02 (Vehicle) | Register vehicle with non-existent owner ID | No customer `CUST-9999` | RegNo: "TN-33-ZZ-9999", Model: "Honda City", Owner: "CUST-9999" | System blocks registration; throws `CustomerNotFoundException` | Handled: "Customer ID CUST-9999 not found" | **PASS** |
| **TC-VEH-03** | FR-02 (Vehicle) | Register vehicle with duplicate registration number | "TN-33-AX-5544" already registered | RegNo: "TN-33-AX-5544", Model: "Maruti Swift", Owner: "CUST-1001" | System rejects duplicate registration number | SQL Unique Constraint caught, warning displayed | **PASS** |
| **TC-APT-01** | FR-03 (Appointment) | Schedule valid service appointment | Vehicle `VEH-101` exists | VehicleID: "VEH-101", Date: "2026-11-20", Time: "10:00 AM", Type: "Periodic Maintenance" | Appointment confirmed with status `SCHEDULED` | Appointment `APT-301` created with `SCHEDULED` status | **PASS** |
| **TC-APT-02** | FR-03 (Appointment) | Schedule appointment on past date | System date: 2026-11-16 | Date: "2025-10-01", Time: "02:00 PM", Type: "Oil Change" | Rejects date; displays error "Appointment cannot be in the past" | Validation triggered; input rejected | **PASS** |
| **TC-APT-03** | FR-03 (Appointment) | Schedule appointment slot conflict | Bay 1 booked at 10:00 AM on 2026-11-20 | Same Date: "2026-11-20", Time: "10:00 AM", Bay 1 | Detects slot conflict; throws `AppointmentConflictException` | Throws `AppointmentConflictException`, suggests next slot | **PASS** |
| **TC-SRV-01** | FR-04 (Maintenance) | Record maintenance work and valid spare parts | `APT-301` scheduled; Stock of Engine Oil = 10 | PartID: "PRT-01" (Engine Oil), Qty: 2, LaborHours: 2.5, Rate: 500, Notes: "Engine oil flushed" | Service status changed to `COMPLETED`; stock decremented to 8 | Service record `SRV-501` saved, parts allocated, stock updated | **PASS** |
| **TC-SRV-02** | FR-04 (Maintenance) | Request spare part quantity exceeding inventory | Brake Pad Stock = 1 | PartID: "PRT-02", Requested Qty: 4 | Rejects part allocation; throws `InsufficientStockException` | Handled: "Only 1 in stock for Brake Pad" | **PASS** |
| **TC-BIL-01** | FR-05 (Billing) | Calculate bill for completed service record | `SRV-501` completed; Parts Cost: 2800, Labor: 1250 | Service ID: "SRV-501", Discount: 5%, GST: 18% | Subtotal: 4050, Taxable: 3847.50, GST: 692.55, Total: 4540.05 | Total calculated accurately: 4540.05, Bill `BIL-801` generated | **PASS** |
| **TC-BIL-02** | FR-05 (Billing) | Attempt to generate bill for `SCHEDULED` service | `APT-302` in `SCHEDULED` status | Service ID: "SRV-502" (Status: SCHEDULED) | Rejects billing; error: "Service record is not completed yet" | Blocked: "Cannot bill an incomplete service" | **PASS** |
| **TC-REP-01** | FR-06 (Reporting) | Generate vehicle-specific service history | Vehicle `VEH-101` has 3 past services | VehicleID: "VEH-101" | Displays chronological list of 3 service records with parts, costs | Formatted table printed with all 3 historical jobs | **PASS** |
| **TC-REP-02** | FR-06 (Reporting) | Generate workshop bay workload analysis | 25 appointments across 30 days | Option: Workload Analysis | Aggregates daily/weekly service volume and bay utilization | Java Streams aggregate counts accurately by status | **PASS** |
| **TC-DS-01** | FR-07 (Data Science) | Python script database retrieval and missing data handling | CSV / DB contains null values | Execute `data_preprocessing.py` | Imputes missing values, eliminates duplicates, converts types | Clean DataFrame output with 0 nulls and correct dtypes | **PASS** |
| **TC-DS-02** | FR-07 (Data Science) | Generate high-resolution Matplotlib/Seaborn charts | Clean DataFrame loaded | Execute `data_visualization.py` | Saves 6 high-res PNG charts into `visualizations/` directory | All 6 charts generated and saved without warnings | **PASS** |

---

## 4. Test Summary & Defect Metrics
- **Total Test Cases Executed:** 16
- **Passed Test Cases:** 16
- **Failed Test Cases:** 0
- **Test Pass Rate:** 100%
- **Conclusion:** The Vehicle Service Management and Maintenance Analysis System satisfies all verification criteria, exhibits robust error resilience against invalid user input, enforces relational data consistency, and performs analytical computations cleanly.
