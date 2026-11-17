# Java Programming Requirements Evaluation
**Department of Information Technology** | **Academic Year:** 2026-2027 (ODD)  
**Course Code & Title:** 25ITT32 – Java Programming  
**Project:** Vehicle Service Management and Maintenance Analysis System  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Syllabus Alignment & Unit-Wise Architecture

The Vehicle Service Management and Maintenance Analysis System is implemented in modern, idiomatic Java, directly reflecting the learning outcomes and syllabus modules of **25ITT32 Java Programming**:

```
+--------------------------------------------------------------------------------------------------+
| Unit 1: Introduction to Java         ==> OOP, Class Fundamentals, Encapsulation, Static, I/O    |
| Unit 2: Inheritance & Interfaces     ==> Package Organization, Abstraction, Interface Contracts  |
| Unit 3: Exceptions & Multithreading  ==> Custom Domain Exceptions, Background Workload Monitor   |
| Unit 4: Strings & Persistence        ==> StringBuilder Invoicing, JDBC / In-Memory Persistence  |
| Unit 5: Collections, Lambdas, Streams==> Stream Filtering, GroupingBy, Reductions, Collectors    |
+--------------------------------------------------------------------------------------------------+
```

---

## 2. Unit-Wise Technical Mapping

### Unit 1: Introduction to Java & OOP Fundamentals
- **Classes and Encapsulation:** All domain entities (`Customer`, `Vehicle`, `ServiceAppointment`, `ServiceRecord`, `SparePart`, `Bill`) enforce private member attributes accessible strictly through getters, setters, and domain business methods.
- **Constructors & Overloading:** Multiple parameterized and default constructors facilitate flexible entity initialization.
- **Static Members:** Static counters and utilities (`DatabaseManager.getInstance()`, `ConsoleHelper.printHeader()`) provide thread-safe singleton state and styling.
- **Console I/O:** Robust console interaction utilizing `java.util.Scanner` with input cleansing, buffer flushing, and type validation.

### Unit 2: Inheritance, Packages, and Interfaces
- **Modular Package Structure:** Organizes code under `com.vehicleservice.*`:
  - `com.vehicleservice.model`
  - `com.vehicleservice.dao`
  - `com.vehicleservice.service`
  - `com.vehicleservice.exception`
  - `com.vehicleservice.thread`
  - `com.vehicleservice.util`
- **Interfaces & Polymorphism:** Service interfaces define decoupled contracts implemented by concrete service providers, enabling clean dependency inversion.

### Unit 3: Exception Handling and Multithreading
- **Custom User-Defined Exceptions:**
  - `VehicleNotFoundException`: Thrown when querying an unregistered vehicle registration number or ID.
  - `AppointmentConflictException`: Thrown when a customer attempts to book a bay already occupied during that time slot.
  - `InsufficientStockException`: Thrown when a technician attempts to allocate more spare parts than currently in inventory.
  - `InvalidDataException`: Thrown when validation checks fail (e.g., negative prices, malformed phone numbers).
- **Try-Catch-Finally Blocks:** Guard all console parsing and persistence operations to prevent application crashes.
- **Multithreaded Programming:**
  - `WorkloadMonitorThread` implements `Runnable` and runs as a background daemon thread with set priorities.
  - Periodic thread checks scan active service records and display live workshop capacity alerts without blocking user interactions.
  - Synchronized blocks ensure thread-safe inventory decrement operations.

### Unit 4: String Handling and Database Persistence
- **String & StringBuilder:** High-performance invoice and report generation uses `StringBuilder` for string concatenation, table formatting, and date formatting.
- **Database Persistence Architecture:** Implements the Data Access Object (DAO) pattern. Persistence methods replicate relational CRUD operations and export CSV datasets directly consumable by the Python analytics engine.

### Unit 5: Generics, Collections Framework, Lambdas & Stream API
- **Collections Framework:** Generic data structures (`List<ServiceRecord>`, `Map<String, Vehicle>`, `Set<String>`) ensure compile-time type safety.
- **Lambda Expressions:** Concise lambda predicates for appointment filtering, price sorting, and conditional updates.
- **Stream API Pipelines:**
  - *Filtering:* `serviceRecords.stream().filter(sr -> sr.getStatus() == ServiceStatus.COMPLETED)`
  - *Mapping & Reduction:* Summing total revenue: `bills.stream().mapToDouble(Bill::getTotalAmount).sum()`
  - *Grouping & Collectors:* Vehicle-wise maintenance aggregation:
    `records.stream().collect(Collectors.groupingBy(ServiceRecord::getVehicleId, Collectors.counting()))`
  - *Sorting:* Sorting vehicles by cumulative expenditure using `.sorted(Comparator.comparingDouble(...).reversed())`.
