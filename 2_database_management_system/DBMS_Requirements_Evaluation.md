# Database Management Systems (DBMS) Requirements Evaluation
**Department of Information Technology** | **Academic Year:** 2026-2027 (ODD)  
**Course Code & Title:** 25ITT31 – Database Management Systems  
**Project:** Vehicle Service Management and Maintenance Analysis System  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Syllabus Alignment & Unit-Wise Mapping

The requirements for Scenario 16 (Vehicle Service Management and Maintenance Analysis System) have been evaluated and mapped directly to the five instructional units of the **25ITT31 Database Management Systems** syllabus:

```
+--------------------------------------------------------------------------------------------------+
| Unit 1: Database Systems & Design     ==> ER Modeling, Relational Schema, Relational Algebra     |
| Unit 2: SQL Operations & Constraints  ==> DDL/DML, Complex Joins, Subqueries, Views, Triggers     |
| Unit 3: Normalization & Optimization  ==> Functional Dependencies, 1NF -> BCNF, B+ Tree Indexing |
| Unit 4: Transaction Management        ==> ACID Properties, Concurrency, Stock Update Locking      |
| Unit 5: NoSQL Databases               ==> Comparison & MongoDB Archival for IoT Diagnostic Logs  |
+--------------------------------------------------------------------------------------------------+
```

---

## 2. Unit-Wise Technical Evaluation

### Unit 1: Database Systems and Design
- **E-R Model Formulation:**
  - Entities: `Customer`, `Vehicle`, `ServiceAppointment`, `ServiceRecord`, `SparePart`, `Bill`.
  - Cardinalities:
    - A `Customer` owns zero or more `Vehicles` ($1 : N$).
    - A `Vehicle` has zero or more `ServiceAppointments` ($1 : N$).
    - An `Appointment` leads to exactly one `ServiceRecord` ($1 : 1$).
    - A `ServiceRecord` utilizes one or more `SpareParts` ($M : N$, resolved using the associative entity `parts_used`).
    - A `ServiceRecord` produces exactly one `Bill` ($1 : 1$).
- **Relational Algebra Mapping:**
  - *Selection ($\sigma$):* Retrieve active services: $\sigma_{\text{status} = 'IN\_PROGRESS'}(\text{service\_records})$
  - *Projection ($\pi$):* List customer contacts: $\pi_{\text{customer\_id}, \text{name}, \text{phone}}(\text{customers})$
  - *Natural Join ($\bowtie$):* View vehicle owner details: $\text{customers} \bowtie_{\text{customers.customer\_id} = \text{vehicles.customer\_id}} \text{vehicles}$

### Unit 2: SQL (Structured Query Language)
- **Data Definition Language (DDL):** Schema definitions with explicit constraints:
  - `PRIMARY KEY` (e.g., `customer_id`, `vehicle_id`, `record_id`).
  - `FOREIGN KEY` with referential integrity (`ON DELETE RESTRICT`, `ON UPDATE CASCADE`).
  - `CHECK` constraints (e.g., `labor_charges >= 0`, `quantity_used > 0`, `year >= 1980`).
  - `UNIQUE` constraints on vehicle registration numbers and phone numbers.
- **Data Manipulation Language (DML):** Robust sample dataset covering diverse service records, appointment schedules, and itemized billing lines.
- **Advanced SQL Constructs:**
  - **Joins:** Multiple inner joins connecting `customers`, `vehicles`, `service_records`, and `bills`.
  - **Aggregates & Grouping:** `COUNT()`, `SUM()`, `AVG()` calculating total revenue and bay workload.
  - **Nested Subqueries:** Identifying vehicles whose maintenance cost exceeds the average center expenditure.
  - **Views:** Pre-compiled SQL views for service history and workload monitoring (`v_vehicle_service_history`, `v_workload_summary`).
  - **Triggers:** Automated trigger on `parts_used` insertion to decrement `spare_parts.stock_quantity` and abort if inventory is insufficient.

### Unit 3: Normalization and Query Optimization
- **Functional Dependencies (FDs):** Formulated from enterprise rules and tested for anomalies.
- **Normalization Journey:**
  - *UNF to 1NF:* Decompose multi-valued spare parts and eliminate repeating repair groups.
  - *1NF to 2NF:* Eliminate partial dependencies by introducing composite key junction tables (`parts_used`).
  - *2NF to 3NF:* Eliminate transitive dependencies (e.g., separating customer phone/address from vehicle records).
  - *BCNF Verification:* Guarantee that for every non-trivial functional dependency $X \rightarrow Y$, $X$ is a superkey.
- **Indexing & Optimization:**
  - B+ Tree indexes created on `vehicle_id`, `appointment_date`, and `payment_status` to ensure $O(\log N)$ search performance during high-concurrency service bay lookups.

### Unit 4: Transaction Management
- **ACID Guarantees during Billing & Inventory Updates:**
  - **Atomicity:** When a service record is marked completed, the parts deduction, bill generation, and appointment status update occur inside a single atomic SQL transaction (`START TRANSACTION ... COMMIT`). If parts deduction fails, the entire transaction issues a `ROLLBACK`.
  - **Consistency:** Stock cannot become negative; bills must equal labor + parts + tax.
  - **Isolation:** Set to `READ COMMITTED` or `REPEATABLE READ` to prevent dirty reads during billing audits.
  - **Durability:** Write-Ahead Logging (WAL) in MySQL InnoDB guarantees transaction durability upon server restart.

### Unit 5: NoSQL Database Consideration (Architectural Extension)
- While relational MySQL is chosen for the ACID-critical core (billing and appointments), modern vehicle maintenance systems can leverage:
  - **MongoDB (Document Store):** Suitable for storing semi-structured vehicle onboard diagnostic (OBD-II) telemetry logs, fault codes, and sensor readings.
  - **Redis (Key-Value Store):** Suitable for caching live bay availability and appointment slot locks during peak booking hours.
