# Foundations of Data Science (FDS) Requirements Evaluation
**Department of Information Technology** | **Academic Year:** 2026-2027 (ODD)  
**Course Code & Title:** 25ITC31 – Foundations of Data Science  
**Project:** Vehicle Service Management and Maintenance Analysis System  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Syllabus Alignment & Unit-Wise Structure

The data analysis and visualization deliverables for Scenario 16 directly align with the five units of the **25ITC31 Foundations of Data Science** curriculum:

```
+--------------------------------------------------------------------------------------------------+
| Unit 1: Data Science Fundamentals    ==> Variables, Central Tendency, Dispersion, Z-Score Anomaly|
| Unit 2: Python Foundations           ==> Jupyter Notebook, Data Structures, CSV / DB File I/O    |
| Unit 3: Data Wrangling               ==> NumPy Arrays, Pandas DataFrames, GroupBy, Pivot Tables  |
| Unit 4: Data Cleaning & Preprocessing==> Missing Values, Duplicates, IQR Outliers, Feature Creation|
| Unit 5: EDA & Visualization          ==> Matplotlib & Seaborn: Line, Bar, Box, Heatmap, Scatter  |
+--------------------------------------------------------------------------------------------------+
```

---

## 2. Unit-Wise Technical Implementation

### Unit 1: Data Science Fundamentals and Data Description
- **Variable Categorization:**
  - *Categorical / Nominal:* `vehicle_type` (SUV, Sedan, Hatchback, Truck, Two-Wheeler), `service_type`, `payment_mode`, `technician_name`.
  - *Categorical / Ordinal:* `service_status` (Scheduled $\rightarrow$ In Progress $\rightarrow$ Completed).
  - *Numerical / Discrete:* `manufacturing_year`, `quantity_used`, `service_visits_count`.
  - *Numerical / Continuous:* `labor_hours`, `labor_charges`, `parts_cost`, `total_amount`.
- **Descriptive Statistics:**
  - Measures of Central Tendency: Mean, Median, and Mode for maintenance costs and labor turnaround hours.
  - Measures of Dispersion: Standard Deviation ($\sigma$), Variance ($\sigma^2$), Interquartile Range ($IQR = Q_3 - Q_1$), and Range.
- **Standard Normal Distribution & Z-Score Analysis:**
  $$z = \frac{x - \mu}{\sigma}$$
  Used to identify high-cost maintenance anomaly vehicles where $|z| > 2.0$.

### Unit 2: Python Foundations for Data Analysis
- Environment: Python 3.10+ / Jupyter Notebook environment (`Vehicle_Maintenance_Analysis.ipynb`).
- Structured libraries: `numpy`, `pandas`, `matplotlib.pyplot`, `seaborn`, `scipy.stats`.
- Modular code architecture: Decoupled into `data_retrieval.py`, `data_preprocessing.py`, and `data_visualization.py`.

### Unit 3: Data Wrangling with NumPy & Pandas
- **DataFrame Indexing & Slicing:** Multi-level indexing on `[vehicle_type, service_type]`.
- **Aggregation and Grouping (`groupby`):**
  - Aggregating maintenance cost and frequency grouped by `vehicle_type`.
  - Workload volume and labor billings grouped by `technician_name`.
- **Pivot Tables:**
  - Cross-tabulation of `service_type` vs `vehicle_type` displaying average expenditure.

### Unit 4: Data Cleaning and Transformation Techniques
- **Missing Value Handling:** Detection of null values in `email`, `address`, or discounts; conditional median imputation for incomplete numerical measurements.
- **Duplicate Detection & Removal:** Checking unique transaction IDs (`record_id`, `bill_id`).
- **Data Type Casting:** Converting timestamps to `datetime64[ns]`, categorical strings to `category` dtypes.
- **Outlier Identification & Treatment:** Using the $1.5 \times IQR$ rule on `total_amount` to isolate extreme repairs without distorting baseline operational distributions.
- **Feature Engineering:**
  - `labor_to_parts_ratio` $= \frac{\text{labor\_charges}}{\text{parts\_cost} + 1}$
  - `effective_tax_pct` $= \frac{\text{tax\_amount}}{\text{subtotal}} \times 100$
  - `service_month` extracted from `service_date` for time-series aggregation.

### Unit 5: Exploratory Data Analysis (EDA) and Visualization Techniques
- **Line Charts (Time-Series):** Monthly center workload and cumulative revenue trends over time.
- **Bar Charts:** Service type popularity and demand distribution across vehicle owners.
- **Box Plots (Seaborn):** Comparative distribution of total maintenance expenditure across vehicle types (`SUV`, `SEDAN`, `HATCHBACK`, `TRUCK`, `TWO_WHEELER`).
- **Scatter Plot with Linear Regression Trendline:** Relationship between spare parts expenditure and labor charges.
- **Correlation Heatmap:** Multi-variable correlation matrix evaluating interactions between labor hours, parts cost, discount, and total bill.
- **Pie / Donut Chart:** Proportional breakdown of payment methods (`UPI`, `CARD`, `CASH`, `NET_BANKING`).
