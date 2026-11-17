# Software Engineering Requirements Evaluation
**Academic Year:** 2026-2027 (ODD) | **Class:** 25IT3A&B  
**Course:** Software Engineering  
**Project:** Vehicle Service Management and Maintenance Analysis System  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Executive Summary & Problem Context
The automobile service industry relies heavily on operational accuracy, timely maintenance tracking, customer satisfaction, and predictive workload scheduling. In manual or legacy spreadsheet-based operations, service centers encounter:
1. **Scheduling bottlenecks and conflicts** due to manual slot allocation.
2. **Fragmented vehicle service history**, impairing diagnostic speed and safety compliance.
3. **Inaccurate billing and inventory depletion**, where spare parts and labor charges are improperly tracked or audited.
4. **Lack of analytical foresight**, preventing management from discovering recurring mechanical failures, peak workload seasons, and customer retention trends.

To address these challenges, this project delivers an end-to-end software solution following the **Software Development Life Cycle (SDLC)**, structured under the IEEE 830 standard for Software Requirements Specification (SRS), Object-Oriented Analysis and Design (OOAD) using UML, and rigorous Verification and Validation (V&V) methodologies.

---

## 2. Requirement Evaluation & Mapping (Syllabus Aligned)

According to standard Software Engineering principles, requirements are systematically evaluated, decomposed, and categorized into Functional Requirements (FR), Non-Functional Requirements (NFR), and System Constraints.

### 2.1 Functional Requirements Breakdown

| Requirement ID | Requirement Description | System Component / Subsystem | Inputs | Expected Output | Complexity |
|---|---|---|---|---|---|
| **FR-01** | Maintain Customer Details (ID, Name, Contact, Email, Address) | Customer Management Subsystem | Customer demographics, phone number, email | Persistent customer record, unique Customer ID | Low |
| **FR-02** | Maintain Vehicle Details (Vehicle ID, Reg No, Model, Vehicle Type, Owner ID) | Vehicle Management Subsystem | Reg No, Make/Model, Year, Vehicle Type (Sedan, SUV, etc.) | Linked vehicle record, validation of unique Reg No | Medium |
| **FR-03** | Schedule Service Appointments & Record Dates/Service Types | Appointment Scheduling Subsystem | Customer ID, Vehicle ID, Preferred Date, Slot, Service Type | Confirmed appointment schedule; conflict-free booking | High |
| **FR-04** | Maintain Service Records (Work performed, spare parts, labor charges, status) | Maintenance Operations Subsystem | Appointment ID, Mechanic ID, Tasks, Spare Parts used, Labor cost | Updated service record, live status (In Progress, Completed) | High |
| **FR-05** | Calculate Service Bills based on labor charges & spare parts | Billing & Invoicing Subsystem | Service Record ID, Parts cost total, Labor charges, Tax/Discount | Itemized invoice, total payable amount, payment status | Medium |
| **FR-06** | Generate Reports (Service history, vehicle maintenance costs, center workload) | Reporting & Business Logic Subsystem | Date ranges, Vehicle ID, Department filter | Tabular/formatted audit reports, workload summaries | Medium |
| **FR-07** | Retrieve DB data, preprocess & generate visualizations via Python | Analytics & Data Science Subsystem | Database connection, SQL queries on services & bills | Cleaned dataframes, descriptive metrics, analytical charts | High |

---

## 3. Non-Functional Requirements (NFR) Evaluation

| Category | Specification | Metrics / Target | SE Quality Attribute |
|---|---|---|---|
| **Performance** | The system must calculate bills and search vehicle history within sub-second latencies. | Response time $\le 500\text{ ms}$ for OLTP queries. | Efficiency |
| **Data Integrity** | Foreign key constraints must prevent orphan service records or billing records without vehicles. | 100% Referential integrity in MySQL RDBMS. | Reliability / Consistency |
| **Security & Access** | Differentiate roles between Service Advisors, Technicians, Billing Managers, and Data Analysts. | Role-Based Access Control (RBAC); parameterized SQL. | Confidentiality & Integrity |
| **Maintainability** | Decoupled Model-View-Controller (MVC) architecture with clear DAO and Service layers. | High cohesion, low coupling, standard OOP packages. | Maintainability / Extensibility |
| **Availability & Fault Tolerance** | Graceful exception handling for input mismatches, missing records, and appointment conflicts. | Zero unexpected termination; robust try-catch-finally. | Robustness |

---

## 4. Requirement Traceability Matrix (RTM)

The Requirement Traceability Matrix guarantees that every functional requirement is accounted for across Design, Implementation, and Testing phases.

| Req ID | SRS Section | Use Case | Design Class | Database Table | Java Module | Python Module | Test Case ID |
|---|---|---|---|---|---|---|---|
| **FR-01** | SRS Sec 3.2.1 | UC-01 | `Customer` | `customers` | `CustomerService.java` | `data_retrieval.py` | TC-CUST-01, 02 |
| **FR-02** | SRS Sec 3.2.2 | UC-02 | `Vehicle` | `vehicles` | `VehicleService.java` | `data_retrieval.py` | TC-VEH-01, 02 |
| **FR-03** | SRS Sec 3.2.3 | UC-03 | `ServiceAppointment` | `service_appointments` | `AppointmentService.java` | `data_preprocessing.py` | TC-APT-01, 02, 03 |
| **FR-04** | SRS Sec 3.2.4 | UC-04 | `ServiceRecord`, `SparePart` | `service_records`, `parts_used` | `MaintenanceService.java` | `data_analysis_and_visualization.py` | TC-SRV-01, 02 |
| **FR-05** | SRS Sec 3.2.5 | UC-05 | `Bill` | `bills` | `BillingService.java` | `data_visualization.py` | TC-BIL-01, 02 |
| **FR-06** | SRS Sec 3.2.6 | UC-06 | `ReportService` | Relational Views | `ReportService.java` | `data_analysis_and_visualization.py` | TC-REP-01, 02 |
| **FR-07** | SRS Sec 3.2.7 | UC-07 | Analytics Engine | Database / CSV extraction | Export routines in `Main.java` | Full Python Analytics Pipeline | TC-DS-01, 02 |

---

## 5. Software Process & SDLC Model Selection
For the Vehicle Service Management and Maintenance Analysis System, the **Iterative / Agile Process Model** is employed:
1. **Phase 1: Requirements Engineering & Modeling (SRS & UML)**: Define problem bounds, specify operational scenarios, formalize use case and class interactions.
2. **Phase 2: Database Architectural Design**: E-R modeling, relational schema normalization (3NF/BCNF), constraints and indexing.
3. **Phase 3: Java Enterprise Core Implementation**: Object-Oriented programming, collections, streams, multi-layered DAO and business logic.
4. **Phase 4: Data Science & Analytics Integration**: Data extraction, wrangling with Pandas/NumPy, EDA and statistical visualization using Matplotlib/Seaborn.
5. **Phase 5: Verification & Validation (V&V)**: White-box and Black-box test case execution and quality verification.
