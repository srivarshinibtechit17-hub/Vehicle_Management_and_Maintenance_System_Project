"""
===============================================================================
DATA RETRIEVAL MODULE
Course: 25ITC31 – Foundations of Data Science (Unit 2 & Practical 1)
Project: Vehicle Service Management and Maintenance Analysis System
Scenario 16 (Register Nos: 16, 46, 76, 106)
===============================================================================
"""

import os
import pandas as pd

DATA_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "data")
DATASET_PATH = os.path.join(DATA_DIR, "vehicle_maintenance_dataset.csv")

def retrieve_from_csv(file_path=DATASET_PATH):
    """
    Retrieves the service and billing dataset from local storage / CSV export.
    Returns a pandas DataFrame.
    """
    if not os.path.exists(file_path):
        raise FileNotFoundError(f"Dataset file not found at: {file_path}")
    
    print(f"[INFO] Loading vehicle maintenance dataset from: {file_path}")
    df = pd.read_csv(file_path)
    print(f"[SUCCESS] Retrieved {len(df)} records with {len(df.columns)} features.")
    return df

def retrieve_from_mysql(host="localhost", user="root", password="", database="vehicle_service_db"):
    """
    Optional live retrieval directly from MySQL RDBMS if database credentials are provided.
    Falls back to CSV if mysql-connector is not installed or MySQL server is unavailable.
    """
    try:
        import mysql.connector
        print(f"[INFO] Connecting to MySQL database '{database}' on {host}...")
        conn = mysql.connector.connect(
            host=host,
            user=user,
            password=password,
            database=database
        )
        query = """
        SELECT 
            sr.record_id, sr.appointment_id, sr.vehicle_id, v.registration_no,
            v.vehicle_type, v.model, v.customer_id, sr.service_date,
            sa.requested_service_type AS service_type, sr.technician_name,
            sr.labor_hours, sr.labor_charges, b.parts_cost_total AS parts_cost,
            b.subtotal, b.tax_amount, b.discount_amount, b.total_amount,
            b.payment_mode, sr.service_status
        FROM service_records sr
        JOIN vehicles v ON sr.vehicle_id = v.vehicle_id
        JOIN service_appointments sa ON sr.appointment_id = sa.appointment_id
        JOIN bills b ON sr.record_id = b.record_id;
        """
        df = pd.read_sql(query, conn)
        conn.close()
        print(f"[SUCCESS] Retrieved {len(df)} rows directly from MySQL.")
        return df
    except Exception as e:
        print(f"[NOTICE] MySQL retrieval skipped ({e}). Falling back to exported CSV.")
        return retrieve_from_csv()

if __name__ == "__main__":
    df = retrieve_from_csv()
    print("\n--- FIRST 5 RECORDS ---")
    print(df.head())
    print("\n--- DATA TYPES & COLUMNS ---")
    print(df.info())
