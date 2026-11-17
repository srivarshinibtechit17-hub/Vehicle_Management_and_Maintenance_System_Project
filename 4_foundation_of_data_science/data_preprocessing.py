"""
===============================================================================
DATA PREPROCESSING & WRANGLING MODULE
Course: 25ITC31 – Foundations of Data Science (Units 1, 3, and 4)
Project: Vehicle Service Management and Maintenance Analysis System
Scenario 16 (Register Nos: 16, 46, 76, 106)
===============================================================================
"""

import numpy as np
import pandas as pd
from data_retrieval import retrieve_from_csv

def preprocess_vehicle_data(df: pd.DataFrame) -> pd.DataFrame:
    """
    Cleans, transforms, and enriches the raw vehicle maintenance dataset
    following 25ITC31 Unit 4 curriculum standards.
    """
    print("\n" + "="*60)
    print("STEP 1: INITIAL DATA QUALITY & INTEGRITY AUDIT")
    print("="*60)
    print(f"Initial Shape: {df.shape[0]} rows, {df.shape[1]} columns")
    
    # 1. Missing Value Detection
    missing_counts = df.isnull().sum()
    print("\nMissing values per column:")
    print(missing_counts[missing_counts > 0] if missing_counts.sum() > 0 else "  No null values detected.")
    
    # Fill any null numerical values with median, strings with 'Unknown'
    num_cols = df.select_dtypes(include=[np.number]).columns
    for col in num_cols:
        if df[col].isnull().sum() > 0:
            median_val = df[col].median()
            df[col].fillna(median_val, inplace=True)
            print(f"  Imputed missing values in '{col}' with median: {median_val}")
    
    # 2. Duplicate Detection & Removal
    dup_count = df.duplicated(subset=['record_id']).sum()
    if dup_count > 0:
        df.drop_duplicates(subset=['record_id'], keep='first', inplace=True)
        print(f"  Removed {dup_count} duplicate record(s).")
    else:
        print("  Zero duplicate service records found.")

    # 3. Data Type Conversions
    print("\nConverting attribute datatypes...")
    df['service_date'] = pd.to_datetime(df['service_date'])
    df['vehicle_type'] = df['vehicle_type'].astype('category')
    df['payment_mode'] = df['payment_mode'].astype('category')
    df['service_status'] = df['service_status'].astype('category')

    # 4. Feature Engineering (Unit 4: Feature Creation & Data Preparation)
    print("\nGenerating engineered analytical features...")
    # Extract Service Month (YYYY-MM)
    df['service_month'] = df['service_date'].dt.strftime('%Y-%m')
    
    # Labor to Parts Cost Ratio
    df['labor_parts_ratio'] = np.round(df['labor_charges'] / (df['parts_cost'] + 1.0), 3)
    
    # Effective Savings from Discount (%)
    df['discount_pct_realized'] = np.round((df['discount_amount'] / (df['subtotal'] + 1e-5)) * 100, 2)
    
    # Labor Charge per Hour
    df['effective_hourly_rate'] = np.round(df['labor_charges'] / df['labor_hours'], 2)

    # 5. Outlier Detection (Unit 1 & Unit 4: Z-Scores and Interquartile Range)
    print("\nEvaluating Outliers via Interquartile Range (IQR) on total_amount...")
    q1 = df['total_amount'].quantile(0.25)
    q3 = df['total_amount'].quantile(0.75)
    iqr = q3 - q1
    lower_bound = q1 - 1.5 * iqr
    upper_bound = q3 + 1.5 * iqr
    outliers = df[(df['total_amount'] < lower_bound) | (df['total_amount'] > upper_bound)]
    print(f"  Q1: ₹{q1:.2f} | Q3: ₹{q3:.2f} | IQR: ₹{iqr:.2f}")
    print(f"  Outlier Bounds: [₹{lower_bound:.2f}, ₹{upper_bound:.2f}]")
    print(f"  Identified {len(outliers)} statistical outlier records (High-complexity jobs).")

    # Compute Standard Z-Scores (Unit 1: Normal Distributions & z-scores)
    mean_cost = df['total_amount'].mean()
    std_cost = df['total_amount'].std()
    df['cost_z_score'] = np.round((df['total_amount'] - mean_cost) / std_cost, 3)

    print("\n" + "="*60)
    print("STEP 2: SUMMARY STATISTICS & DATA WRANGLING")
    print("="*60)
    print(df[['labor_hours', 'labor_charges', 'parts_cost', 'total_amount', 'cost_z_score']].describe().round(2))

    return df

def generate_pivot_analysis(df: pd.DataFrame):
    """
    Computes aggregation matrices and pivot tables (Unit 3).
    """
    print("\n--- PIVOT TABLE: AVERAGE TOTAL MAINTENANCE COST (₹) BY VEHICLE TYPE & SERVICE ---")
    pivot = pd.pivot_table(
        df,
        values='total_amount',
        index='vehicle_type',
        columns='payment_mode',
        aggfunc=['count', 'mean'],
        fill_value=0
    )
    print(pivot.round(2))
    return pivot

if __name__ == "__main__":
    raw_df = retrieve_from_csv()
    clean_df = preprocess_vehicle_data(raw_df)
    generate_pivot_analysis(clean_df)
