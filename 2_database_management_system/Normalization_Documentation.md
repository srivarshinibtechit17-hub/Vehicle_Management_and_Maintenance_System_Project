# Database Normalization Documentation
## Vehicle Service Management and Maintenance Analysis System
**Department of Information Technology** | **Academic Year:** 2026-2027 (ODD)  
**Course Code & Title:** 25ITT31 – Database Management Systems (Unit 3)  
**Scenario Assignment:** Scenario 16 (Register Nos: 16, 46, 76, 106)  

---

## 1. Overview & Theoretical Foundations

Database normalization is the formal process of structuring relational database schemas to minimize data redundancy, eliminate operational anomalies (Insertion, Update, and Deletion anomalies), and ensure data consistency.

### 1.1 Armstrong's Axioms and Inference Rules
Given a set of functional dependencies $F$ over relation $R$:
1. **Reflexivity Rule:** If $Y \subseteq X$, then $X \rightarrow Y$.
2. **Augmentation Rule:** If $X \rightarrow Y$, then $XZ \rightarrow YZ$ for any attribute set $Z$.
3. **Transitivity Rule:** If $X \rightarrow Y$ and $Y \rightarrow Z$, then $X \rightarrow Z$.
4. **Decomposition (Projectivity) Rule:** If $X \rightarrow YZ$, then $X \rightarrow Y$ and $X \rightarrow Z$.
5. **Union (Additivity) Rule:** If $X \rightarrow Y$ and $X \rightarrow Z$, then $X \rightarrow YZ$.
6. **Pseudotransitivity Rule:** If $X \rightarrow Y$ and $WY \rightarrow Z$, then $WX \rightarrow Z$.

---

## 2. Universal Relation & Unnormalized Form (UNF)

Suppose an automobile service invoice initially records all data in a single flat structure:

$$\begin{aligned}
\text{SERVICE\_INVOICE\_UNF} = \{ &\text{customer\_id}, \text{customer\_name}, \text{customer\_phone}, \text{customer\_address}, \\
&\text{vehicle\_id}, \text{registration\_no}, \text{vehicle\_model}, \text{vehicle\_type}, \text{mfg\_year}, \\
&\text{appointment\_id}, \text{appointment\_date}, \text{time\_slot}, \text{service\_type}, \\
&\text{record\_id}, \text{technician\_name}, \text{work\_performed}, \text{labor\_hours}, \text{hourly\_rate}, \text{labor\_charges}, \\
&\{\text{part\_id}, \text{part\_name}, \text{unit\_price}, \text{quantity\_used}, \text{total\_part\_cost}\}^*, \\
&\text{bill\_id}, \text{bill\_date}, \text{parts\_cost\_total}, \text{subtotal}, \text{tax\_amount}, \text{discount}, \text{total\_bill}, \text{payment\_status}\}
\end{aligned}$$

### Anomalies in UNF:
- **Redundancy:** Customer demographics repeated for every service visit.
- **Insertion Anomaly:** Cannot record a new vehicle until it books a service. Cannot stock a spare part without an active invoice.
- **Update Anomaly:** Changing a customer's phone number requires updating dozens of historic service lines.
- **Deletion Anomaly:** Deleting a bill deletes the vehicle's historical maintenance record.

---

## 3. Step-by-Step Normalization Process

### 3.1 First Normal Form (1NF)
**Rule:** A relation $R$ is in 1NF if and only if all attribute values are atomic (no repeating groups, no multi-valued arrays, no composite sets).

**Action:** Flatten the repeating group of spare parts into individual tuples and define the initial candidate key:
$$\text{Composite Candidate Key} = \{\text{record\_id}, \text{part\_id}\}$$

Every cell contains a single atomic value. However, high redundancy exists, and partial key dependencies remain.

---

### 3.2 Second Normal Form (2NF)
**Rule:** A relation $R$ is in 2NF if and only if it is in 1NF and every non-prime attribute is **fully functionally dependent** on the primary candidate key (no partial functional dependencies $X \rightarrow Y$ where $X \subset \text{Key}$).

**Identified Partial Dependencies on $\{\text{record\_id}, \text{part\_id}\}$:**
- $\text{record\_id} \rightarrow \text{appointment\_id}, \text{vehicle\_id}, \text{work\_performed}, \text{labor\_hours}, \text{labor\_charges}$ (Depends only on part of the composite key).
- $\text{part\_id} \rightarrow \text{part\_name}, \text{unit\_price}$ (Depends only on `part_id`).
- $\{\text{record\_id}, \text{part\_id}\} \rightarrow \text{quantity\_used}, \text{total\_part\_cost}$ (Fully functionally dependent).

**Decomposition into 2NF:**
1. $\text{PARTS\_INVENTORY}(\underline{\text{part\_id}}, \text{part\_name}, \text{unit\_price}, \text{stock\_quantity})$
2. $\text{PARTS\_USED}(\underline{\text{record\_id}, \text{part\_id}}, \text{quantity\_used}, \text{unit\_price\_at\_service}, \text{total\_part\_cost})$
3. $\text{SERVICE\_MASTER}(\underline{\text{record\_id}}, \text{appointment\_id}, \text{vehicle\_id}, \text{customer\_id}, \text{customer\_name}, \text{phone}, \dots, \text{bill\_id}, \text{total\_bill})$

---

### 3.3 Third Normal Form (3NF)
**Rule:** A relation $R$ is in 3NF if and only if it is in 2NF and **no non-prime attribute is transitively dependent** on the primary key ($X \rightarrow Y \rightarrow Z$ where $Z$ is not a prime attribute).

**Identified Transitive Dependencies in `SERVICE_MASTER`:**
- $\text{record\_id} \rightarrow \text{vehicle\_id} \rightarrow \text{registration\_no}, \text{model}, \text{vehicle\_type}, \text{customer\_id}$
- $\text{vehicle\_id} \rightarrow \text{customer\_id} \rightarrow \text{customer\_name}, \text{phone}, \text{email}, \text{address}$
- $\text{record\_id} \rightarrow \text{appointment\_id} \rightarrow \text{appointment\_date}, \text{time\_slot}, \text{service\_type}$
- $\text{record\_id} \rightarrow \text{bill\_id} \rightarrow \text{bill\_date}, \text{subtotal}, \text{tax\_amount}, \text{total\_bill}$

**Decomposition into 3NF Relations:**
1. **`CUSTOMERS`**
   $$\underline{\text{customer\_id}} \rightarrow \text{full\_name}, \text{phone}, \text{email}, \text{address}, \text{registered\_date}$$
2. **`VEHICLES`**
   $$\underline{\text{vehicle\_id}} \rightarrow \text{registration\_no}, \text{model}, \text{vehicle\_type}, \text{manufacturing\_year}, \text{customer\_id}$$
3. **`SERVICE_APPOINTMENTS`**
   $$\underline{\text{appointment\_id}} \rightarrow \text{vehicle\_id}, \text{customer\_id}, \text{appointment\_date}, \text{time\_slot}, \text{requested\_service\_type}, \text{status}$$
4. **`SERVICE_RECORDS`**
   $$\underline{\text{record\_id}} \rightarrow \text{appointment\_id}, \text{vehicle\_id}, \text{technician\_name}, \text{work\_performed}, \text{labor\_hours}, \text{labor\_charges}, \text{service\_status}, \text{service\_date}$$
5. **`SPARE_PARTS`**
   $$\underline{\text{part\_id}} \rightarrow \text{part\_name}, \text{part\_category}, \text{unit\_price}, \text{stock\_quantity}, \text{reorder\_level}$$
6. **`PARTS_USED`**
   $$\underline{\text{usage\_id}} \rightarrow \text{record\_id}, \text{part\_id}, \text{quantity\_used}, \text{unit\_price\_at\_service}, \text{total\_part\_cost}$$
7. **`BILLS`**
   $$\underline{\text{bill\_id}} \rightarrow \text{record\_id}, \text{bill\_date}, \text{parts\_cost\_total}, \text{labor\_charges\_total}, \text{subtotal}, \text{tax\_rate}, \text{tax\_amount}, \text{discount\_amount}, \text{total\_amount}, \text{payment\_status}, \text{payment\_mode}$$

---

### 3.4 Boyce-Codd Normal Form (BCNF)
**Rule:** A relation $R$ is in BCNF if and only if for every non-trivial functional dependency $X \rightarrow Y$, the determinant $X$ is a **Superkey** of $R$.

**Evaluation:**
- In `CUSTOMERS`: Determinants are `customer_id` (Primary Key) and `phone` (Candidate Key / UNIQUE). Both are superkeys. $\implies$ **In BCNF**.
- In `VEHICLES`: Determinants are `vehicle_id` (Primary Key) and `registration_no` (Candidate Key / UNIQUE). Both are superkeys. $\implies$ **In BCNF**.
- In `SERVICE_APPOINTMENTS`: Determinant is `appointment_id` (Primary Key). $\implies$ **In BCNF**.
- In `SERVICE_RECORDS`: Determinant is `record_id` (Primary Key). $\implies$ **In BCNF**.
- In `SPARE_PARTS`: Determinant is `part_id` (Primary Key). $\implies$ **In BCNF**.
- In `PARTS_USED`: Determinant is `usage_id` (Primary Key), and $\{\text{record\_id}, \text{part\_id}\}$ is a unique composite superkey. $\implies$ **In BCNF**.
- In `BILLS`: Determinant is `bill_id` (Primary Key), and `record_id` is an alternate unique candidate key. Both are superkeys. $\implies$ **In BCNF**.

**Conclusion:** All 7 relations strictly satisfy BCNF.

---

## 4. Formal Verification: Lossless Join & Dependency Preservation

### 4.1 Lossless Join Decomposition
A decomposition of relation $R$ into $R_1$ and $R_2$ is lossless with respect to a set of functional dependencies $F$ if and only if:
$$(R_1 \cap R_2) \rightarrow R_1 \quad \text{OR} \quad (R_1 \cap R_2) \rightarrow R_2$$

*Proof for `CUSTOMERS` $\bowtie$ `VEHICLES`:*
- $R_1 = \text{CUSTOMERS}$, $R_2 = \text{VEHICLES}$.
- $R_1 \cap R_2 = \{\text{customer\_id}\}$.
- Since $\text{customer\_id}$ is the primary key of $R_1$, $\text{customer\_id} \rightarrow \text{CUSTOMERS}$.
- Therefore, $(R_1 \cap R_2) \rightarrow R_1$ holds true. The decomposition is **guaranteed lossless**.

### 4.2 Dependency Preservation
A decomposition is dependency-preserving if the closure of the union of functional dependencies in all individual relations equals the closure of the original set $F$:
$$(F_1 \cup F_2 \cup \dots \cup F_k)^+ = F^+$$
Every original functional dependency $X \rightarrow Y$ in the problem domain is preserved inside one of the individual normalized tables without requiring cross-table join assertions.

---

## 5. Summary Matrix of Normalized Relations

| Relation Name | Normal Form Achieved | Primary Key | Alternate / Candidate Keys | Foreign Keys |
|---|---|---|---|---|
| `customers` | BCNF | `customer_id` | `phone` | None |
| `vehicles` | BCNF | `vehicle_id` | `registration_no` | `customer_id` $\rightarrow$ `customers` |
| `spare_parts` | BCNF | `part_id` | None | None |
| `service_appointments` | BCNF | `appointment_id` | None | `vehicle_id`, `customer_id` |
| `service_records` | BCNF | `record_id` | None | `appointment_id`, `vehicle_id` |
| `parts_used` | BCNF | `usage_id` | `(record_id, part_id)` | `record_id`, `part_id` |
| `bills` | BCNF | `bill_id` | `record_id` | `record_id` $\rightarrow$ `service_records` |
