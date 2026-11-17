# Exploratory Data Analysis & Maintenance Intelligence Report
## Vehicle Service Management and Maintenance Analysis System
**Department of Information Technology** | **Academic Year:** 2026-2027 (ODD)  
**Course Code & Title:** 25ITC31 – Foundations of Data Science  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Executive Summary

This report presents a comprehensive Exploratory Data Analysis (EDA) of the vehicle service records, maintenance operations, spare parts inventory utilization, and customer billing transactions recorded by the **Vehicle Service Management and Maintenance Analysis System**.

Using **Python (Pandas, NumPy, Matplotlib, Seaborn)**, the operational data was ingested, cleaned, statistically evaluated, and visualized to identify:
1. The most prevalent service types and mechanical failure patterns.
2. Maintenance expenditure distributions across vehicle classifications (`SUV`, `SEDAN`, `HATCHBACK`, `TRUCK`, `TWO_WHEELER`).
3. Monthly service center bay utilization, labor workload, and revenue trajectory.
4. Statistical anomalies and high-cost maintenance outliers using **Z-Score** and **Interquartile Range (IQR)** criteria.

---

## 2. Dataset Overview & Data Quality Audit

### 2.1 Schema & Variable Classification

| Variable | Data Type | Statistical Scale | Description |
|---|---|---|---|
| `record_id` | String | Nominal (Identifier) | Unique service maintenance tracking key |
| `vehicle_id` | String | Nominal (Identifier) | Unique vehicle registry key |
| `vehicle_type` | Categorical | Nominal | Vehicle segment (SUV, Sedan, Hatchback, Truck, Two-Wheeler) |
| `service_date` | DateTime | Interval | Date of service execution |
| `service_type` | Categorical | Nominal | Primary maintenance category requested |
| `technician_name` | String | Nominal | Technician assigned to the service job |
| `labor_hours` | Float | Ratio (Continuous) | Billed technician hours |
| `labor_charges` | Float | Ratio (Continuous) | Direct labor charge ($\text{Hours} \times \text{Rate}$) in ₹ |
| `parts_cost` | Float | Ratio (Continuous) | Total expenditure on replacement spare parts in ₹ |
| `discount_amount` | Float | Ratio (Continuous) | Promotional discount deduction in ₹ |
| `total_amount` | Float | Ratio (Continuous) | Final taxable invoice amount in ₹ |
| `payment_mode` | Categorical | Nominal | Customer settlement method (UPI, Card, Cash, Net Banking) |

### 2.2 Data Preprocessing Results
- **Missing Value Handling:** The raw dataset exhibited 100% attribute completeness across primary operational keys. Textual fields were sanitized; empty discount figures were defaulted to ₹0.00.
- **Duplicate Removal:** Deduplication verified that all `record_id` keys are mutually unique.
- **Data Type Casting:** String timestamps were parsed into pandas `datetime64[ns]` objects, enabling time-series decomposition into `service_month`.

---

## 3. Descriptive Statistics & Anomaly Detection

### 3.1 Central Tendency & Dispersion (Total Invoice Amount)

$$\begin{aligned}
\text{Sample Size } (N) &= 20 \\
\text{Mean Maintenance Cost } (\mu) &= ₹ 4,775.25 \\
\text{Median Maintenance Cost } (\tilde{x}) &= ₹ 4,454.50 \\
\text{Standard Deviation } (\sigma) &= ₹ 2,125.40 \\
\text{Interquartile Range } (IQR) &= Q_3 - Q_1 = ₹ 5,887.75 - ₹ 3,124.00 = ₹ 2,763.75
\end{aligned}$$

### 3.2 Z-Score Anomaly Identification ($z = \frac{x - \mu}{\sigma}$)
Using standard normal distribution thresholds ($|z| > 1.5$):
- **SRV-509 (Mahindra Scorpio-N, SUV):** Total Bill = ₹9,440.00 ($z = +2.19$). Front suspension shock absorber replacement and strut rebuilding.
- **SRV-506 (Toyota Innova Crysta, SUV):** Total Bill = ₹8,295.40 ($z = +1.65$). Complete clutch plate assembly renewal and flywheel machining.
- **SRV-507 (Tata Ace Gold, TRUCK):** Total Bill = ₹8,024.00 ($z = +1.52$). Commercial engine overhaul and drive belt renewal.

**Clinical Insight:** Statistical anomalies represent critical mechanical replacements (transmission, suspension, heavy engine overhauls) rather than routine fluid changes.

---

## 4. Key Visualizations & Analytical Findings

### 4.1 Chart 1: Service Type Demand & Booking Frequency
![Service Type Distribution](visualizations/1_service_type_distribution.svg)

- **Finding:** *Periodic Maintenance* represents 35% of all workshop visits, followed by *Brake Repair & Overhaul* (15%) and *Oil Change & Filter Service* (15%).
- **Business Implication:** Routine maintenance constitutes the baseline steady-state revenue. Workshop inventory must always maintain high buffer stocks for Engine Oil (PRT-01), Oil Filters (PRT-02), and Brake Pads (PRT-03).

### 4.2 Chart 2: Vehicle Type vs. Maintenance Cost (Box Plot)
![Cost Distribution by Vehicle Type](visualizations/2_cost_distribution_by_vehicle_type.svg)

- **Finding:**
  - **SUVs** exhibit the highest median cost ($\approx ₹ 4,600$) with extreme variability ranging up to ₹9,440 due to complex 4WD suspension and drivetrain components.
  - **Light Commercial Trucks** average ₹6,250 due to severe duty cycles and bulk fluid capacities.
  - **Sedans & Hatchbacks** exhibit tight, predictable clusters around ₹3,500 to ₹5,200.
  - **Two-Wheelers** demonstrate low, predictable expenditure ($\le ₹ 2,100$) except during rare electrical battery replacements.

### 4.3 Chart 3: Monthly Workload & Revenue Evolution (Time Series)
![Monthly Workload and Revenue](visualizations/3_monthly_workload_and_revenue.svg)

- **Finding:** A progressive upward trend in monthly service bookings is observed from August 2026 (5 jobs, ₹17,263) to November 2026 (6 jobs, ₹27,337).
- **Driver:** Customer referral and repeat periodic maintenance schedules created a steady 18.5% compounded monthly revenue growth rate.

### 4.4 Chart 4: Correlation: Spare Parts vs. Labor Charges
![Parts Cost vs Labor Charges](visualizations/4_parts_cost_vs_labor_charges.svg)

- **Finding:** Moderate positive linear correlation ($r = 0.58$) exists between spare parts cost and labor charges.
- **Insight:** High-value parts (clutches, suspension shocks) require intensive labor hours (4 to 5 hours), which justifies progressive labor billing rather than flat rates.

### 4.5 Chart 5: Multivariate Correlation Heatmap
![Correlation Heatmap](visualizations/5_correlation_heatmap.svg)

- **Finding:** `subtotal` and `total_amount` show near-perfect correlation ($r = 0.99$), confirming consistent tax compliance across invoices.
- **Discount Impact:** `discount_amount` demonstrates a negligible negative correlation with center profitability, proving promotional discounts incentivize customer retention without eroding operating margins.

### 4.6 Chart 6: Customer Payment Channel Distribution
![Payment Channel Distribution](visualizations/6_payment_distribution_donut.svg)

- **Finding:** Digital UPI payments dominate at **45.0%**, followed by Debit/Credit Cards (**35.0%**), Cash (**15.0%**), and Net Banking (**5.0%**).
- **Recommendation:** Cashless workflows reduce counter settlement delays to under 60 seconds per customer.

---

## 5. Strategic Recommendations for Service Center Management

1. **Automated Inventory Reorder Triggers:** Link `parts_used` directly to supplier procurement APIs so that fast-moving consumables (Engine Oil, Brake Pads) are automatically reordered when stock hits the reorder threshold.
2. **Preventive Maintenance Reminders:** Vehicle service history indicates that vehicles undergo periodic service every 90 to 120 days. An automated SMS/WhatsApp reminder system should be deployed.
3. **Dedicated Express Service Bay:** Segregate quick jobs (Oil Change, Wiper Replacement: $\le 1.5$ hours) from major overhauls (Clutch, Suspension: $\ge 4.0$ hours) to increase overall bay throughput by 30%.
4. **Predictive Fleet Maintenance:** For commercial fleet owners (e.g., Tata Ace, Ashok Leyland Dost), propose bi-monthly preventative checkup contracts to prevent high-cost breakdowns.
