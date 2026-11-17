# Vehicle Service Management and Maintenance Analysis System
**Academic Year:** 2026-2027 (ODD) | **Class:** 25IT3A&B  
**Department of Information Technology** | **Velalar College of Engineering and Technology, Erode - 12**  
**Assessment:** Activity - Project-Based Assessment  
**Assigned Scenario:** **Scenario 16: Vehicle Service Management and Maintenance Analysis System**  
**Assigned Register Numbers:** 16, 46, 76, 106  

---

## 1. Project Overview & Problem Statement

An automobile service center manages customer information, vehicle details, service appointments, maintenance records, and billing information. Maintaining these records manually leads to scheduling conflicts, difficulties tracking vehicle service histories, inventory leakage, and delays in identifying frequently serviced vehicles and recurring mechanical failures.

This project delivers an integrated **Vehicle Service Management and Maintenance Analysis System** designed and evaluated across four core computer science subjects:
1. **Software Engineering**
2. **Database Management Systems (25ITT31)**
3. **Java Programming (25ITT32)**
4. **Foundations of Data Science (25ITC31)**

The repository is modularly structured into **four distinct category folders**, providing end-to-end deliverables from IEEE-standard specification documents and normalized SQL databases to an enterprise Java core and Python data science visualization pipelines.

---

## 2. Four Category Requirements & Syllabus Evaluation

```
========================================================================================================================
CATEGORY                          SYLLABUS & STANDARDS ALIGNMENT            KEY DELIVERABLES
========================================================================================================================
1. Software Engineering           IEEE Std 830-1998, OOAD UML Modeling,     - SRS Document (IEEE 830)
                                  Verification & Validation (V&V), RTM      - Use Case, Class & Architecture Diagrams
                                                                            - Test Plan & Comprehensive Test Suite
                                                                            - SE Requirements Evaluation Matrix
------------------------------------------------------------------------------------------------------------------------
2. Database Management System     25ITT31 (Units 1 - 5)                     - E-R Model & Relational Schema
   (DBMS)                         E-R Diagrams, Relational Algebra, DDL/DML, - Normalization Document (1NF -> BCNF)
                                  Functional Dependencies, 3NF/BCNF, Joins,  - schema.sql (DDL, Views, Triggers)
                                  Views, Triggers, ACID Transactions        - sample_data.sql (Realistic DML Records)
                                                                            - analytical_queries.sql (Reports & OLAP)
------------------------------------------------------------------------------------------------------------------------
3. Java Programming               25ITT32 (Units 1 - 5)                     - Full OOP Source Code (com.vehicleservice.*)
                                  Classes, Inheritance, Custom Exceptions,   - Multithreaded Background Monitor Thread
                                  Thread Model, DAO Pattern, Collections,   - Java 8+ Stream API Analytics & Reports
                                  Stream API (filter/group/map/reduce)      - Interactive Console CLI & Demo Mode
                                                                            - sample_inputs_outputs.txt & build script
------------------------------------------------------------------------------------------------------------------------
4. Foundation of Data Science     25ITC31 (Units 1 - 5)                     - CSV Datasets (Customers, Vehicles, Bills)
   (FDS)                          Descriptive Statistics, Z-Score Outliers, - data_retrieval.py (DB / CSV connector)
                                  Data Wrangling, Cleaning, Matplotlib &    - data_preprocessing.py (IQR, Scaling)
                                  Seaborn EDA Visualizations                - data_visualization.py (6 PNG Charts)
                                                                            - Jupyter Notebook (.ipynb) & Report
========================================================================================================================
```

---

## 3. Directory Structure

```
c:\Activity\
│
├── README.md                               <-- Master Project Documentation & Execution Guide
│
├── 1_software_engineering/                 <-- Category 1: Software Engineering
│   ├── SE_Requirements_Evaluation.md       <-- Detailed syllabus evaluation & traceability matrix (RTM)
│   ├── SRS_Document.md                     <-- IEEE 830 Software Requirements Specification
│   ├── Design_Diagrams.md                  <-- Design diagrams documentation with embedded visuals
│   ├── Testing_Document.md                 <-- Test strategies, ECP/BVA techniques, and 16 test cases
│   └── diagrams/                           <-- Visual Engineering Diagrams (All 7 diagrams)
│       ├── Use_Case_Diagram.jpg            <-- Use Case Model (Actors & Boundary)
│       ├── Class_Diagram.jpg               <-- Object-Oriented Class Hierarchy
│       ├── System_Architecture_Diagram.jpg <-- 4-Tier Layered Architecture
│       ├── Sequence_Diagram.jpg            <-- Chronological Interaction Workflows
│       ├── Activity_Diagram.jpg            <-- Operational Process & Decision Flow
│       ├── DFD_Level_0.jpg                 <-- Context-Level Data Flow Diagram
│       └── DFD_Level_1.jpg                 <-- Decomposed Functional Data Flow Diagram
│
├── 2_database_management_system/           <-- Category 2: Database Management System (25ITT31)
│   ├── run_dbms.bat                        <-- Runnable Double-Click Script for SQL Execution
│   ├── dbms_execution_output.txt           <-- Complete SQL execution log & analytical query outputs
│   ├── dbms_execution_screenshot.png       <-- Terminal execution screenshot
│   ├── DBMS_Requirements_Evaluation.md     <-- Syllabus evaluation across Units 1 to 5
│   ├── ER_and_Relational_Model.md          <-- E-R Diagram documentation & Relational Data Dictionary
│   ├── Normalization_Documentation.md      <-- Rigorous UNF -> 1NF -> 2NF -> 3NF -> BCNF decomposition
│   ├── schema.sql                          <-- DDL: Tables, constraints, indexes, views, triggers
│   ├── sample_data.sql                     <-- DML: 50+ realistic records across all tables
│   ├── analytical_queries.sql              <-- FR-06 reports, Joins, Subqueries, Window functions
│   └── diagrams/                           <-- Visual Database Diagrams
│       └── ER_Diagram.jpg                  <-- Visual Entity-Relationship Model (Crow's Foot notation)
│
├── 3_java_programming/                     <-- Category 3: Java Programming (25ITT32)
│   ├── run_java.bat                        <-- Runnable Double-Click Script for Java Execution
│   ├── compile_and_run.bat                 <-- One-click batch compilation and execution script
│   ├── java_execution_output.txt           <-- Complete Java console execution trace (Text File)
│   ├── java_execution_screenshot.png       <-- Terminal execution screenshot
│   ├── Java_Requirements_Evaluation.md    <-- Syllabus evaluation across Units 1 to 5
│   ├── sample_inputs_outputs.txt           <-- Formatted console input/output execution logs
│   └── src/com/vehicleservice/             <-- Modular Java source package
│       ├── model/                          <-- Domain Entities (Customer, Vehicle, Bill, Parts, etc.)
│       │   ├── Customer.java
│       │   ├── Vehicle.java
│       │   ├── VehicleType.java
│       │   ├── ServiceAppointment.java
│       │   ├── ServiceRecord.java
│       │   ├── ServiceStatus.java
│       │   ├── SparePart.java
│       │   └── Bill.java
│       ├── exception/                      <-- User-Defined Custom Domain Exceptions (Unit 3)
│       │   ├── VehicleNotFoundException.java
│       │   ├── AppointmentConflictException.java
│       │   ├── InsufficientStockException.java
│       │   └── InvalidDataException.java
│       ├── dao/                            <-- Data Access Object & Persistence Repository (Unit 4)
│       │   └── DatabaseManager.java
│       ├── service/                        <-- Business Logic & Stream API Reporting (Units 1 & 5)
│       │   ├── CustomerService.java
│       │   ├── VehicleService.java
│       │   ├── AppointmentService.java
│       │   ├── MaintenanceService.java
│       │   ├── BillingService.java
│       │   └── ReportService.java
│       ├── thread/                         <-- Multithreading Daemon Monitor (Unit 3)
│       │   └── WorkloadMonitorThread.java
│       ├── util/                           <-- Console input helpers & styling
│       │   └── ConsoleHelper.java
│       └── Main.java                       <-- Main Application Entry Point & Menu Controller
│
└── 4_foundation_of_data_science/           <-- Category 4: Foundations of Data Science (25ITC31)
    ├── run_fds.bat                         <-- Runnable Double-Click Script for Data Science Execution
    ├── fds_execution_output.txt            <-- Complete Python Data Science execution trace (Text File)
    ├── fds_execution_screenshot.png        <-- Terminal execution screenshot
    ├── FDS_Requirements_Evaluation.md      <-- Syllabus evaluation across Units 1 to 5
    ├── Data_Science_Report.md              <-- Full analytical report, EDA findings & recommendations
    ├── Vehicle_Maintenance_Analysis.ipynb  <-- Complete Jupyter Notebook for interactive EDA
    ├── data_retrieval.py                   <-- Module for DB / CSV dataset extraction
    ├── data_preprocessing.py               <-- Cleaning, IQR & Z-score outlier detection, feature engineering
    ├── data_visualization.py               <-- Matplotlib & Seaborn chart generator (6 charts)
    ├── data/                               <-- Project CSV Datasets
    │   ├── customers.csv
    │   ├── vehicles.csv
    │   └── vehicle_maintenance_dataset.csv
    └── visualizations/                     <-- Generated SVG & PNG publication charts
        ├── 1_service_type_distribution.svg
        ├── 2_cost_distribution_by_vehicle_type.svg
        ├── 3_monthly_workload_and_revenue.svg
        ├── 4_parts_cost_vs_labor_charges.svg
        ├── 5_correlation_heatmap.svg
        └── 6_payment_distribution_donut.svg
```

---

## 4. Setup & Execution Instructions

### 4.1 Database Setup (MySQL)
1. Ensure **MySQL Server 8.0+** is installed and running.
2. Open MySQL Command Line Client or MySQL Workbench:
   ```sql
   -- 1. Execute schema definition script
   SOURCE c:/Activity/2_database_management_system/schema.sql;

   -- 2. Populate sample data
   SOURCE c:/Activity/2_database_management_system/sample_data.sql;

   -- 3. Run analytical queries and reports
   SOURCE c:/Activity/2_database_management_system/analytical_queries.sql;
   ```

### 4.2 Java Application Execution
1. Open PowerShell or Command Prompt.
2. Navigate to the Java project folder:
   ```cmd
   cd c:\Activity\3_java_programming
   ```
3. Compile all packages:
   ```cmd
   if not exist "bin" mkdir "bin"
   javac -d bin src\com\vehicleservice\model\*.java src\com\vehicleservice\exception\*.java src\com\vehicleservice\dao\*.java src\com\vehicleservice\service\*.java src\com\vehicleservice\thread\*.java src\com\vehicleservice\util\*.java src\com\vehicleservice\Main.java
   ```
4. Run the application:
   - **Interactive CLI Menu:**
     ```cmd
     java -cp bin com.vehicleservice.Main
     ```
   - **Automated End-to-End Simulation:**
     ```cmd
     java -cp bin com.vehicleservice.Main --demo
     ```
   *(Or simply double-click `compile_and_run.bat` on Windows).*

### 4.3 Python Data Science & Visualization Pipeline
1. Navigate to the Data Science directory:
   ```cmd
   cd c:\Activity\4_foundation_of_data_science
   ```
2. Install necessary libraries:
   ```bash
   pip install pandas numpy matplotlib seaborn scipy
   ```
3. Execute the pipeline scripts:
   - **Data Cleaning & Wrangling:**
     ```bash
     python data_preprocessing.py
     ```
   - **Generate Visualizations:**
     ```bash
     python data_visualization.py
     ```
   - **Interactive Jupyter Notebook:**
     ```bash
     jupyter notebook Vehicle_Maintenance_Analysis.ipynb
     ```

---

## 5. GitHub Submission Deliverables Checklist

- [x] **Deliverable 1:** SRS Document, Use Case Diagram, Class Diagram, System Architecture, and Testing Suite (`1_software_engineering/`).
- [x] **Deliverable 2:** E-R Diagram, Relational Data Model, Normalization Documentation (1NF through BCNF), and SQL scripts (`2_database_management_system/`).
- [x] **Deliverable 3:** Java Source Code (complete package hierarchy), build batch script, and sample execution inputs/outputs (`3_java_programming/`).
- [x] **Deliverable 4:** Python code for database retrieval, preprocessing, statistical analysis, 6 publication-ready charts, and Jupyter Notebook (`4_foundation_of_data_science/`).
- [x] **Deliverable 5:** Root `README.md` detailing project architecture, requirements evaluation, and execution steps.
