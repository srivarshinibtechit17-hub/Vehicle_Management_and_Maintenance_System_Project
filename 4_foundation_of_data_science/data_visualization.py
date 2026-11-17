"""
===============================================================================
EXPLORATORY DATA ANALYSIS (EDA) & VISUALIZATION MODULE
Course: 25ITC31 – Foundations of Data Science (Unit 5)
Project: Vehicle Service Management and Maintenance Analysis System
Scenario 16 (Register Nos: 16, 46, 76, 106)
===============================================================================
"""

import os
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import pandas as pd
from data_retrieval import retrieve_from_csv
from data_preprocessing import preprocess_vehicle_data

# Set visual aesthetic theme
sns.set_theme(style="whitegrid", palette="muted")
plt.rcParams.update({'font.sans-serif': 'DejaVu Sans', 'font.size': 11})

OUTPUT_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "visualizations")
os.makedirs(OUTPUT_DIR, exist_ok=True)

def generate_visualizations(df: pd.DataFrame):
    """
    Generates publication-quality charts demonstrating all techniques from Unit 5.
    """
    print(f"\n[INFO] Generating analytical charts in: {OUTPUT_DIR}")

    # -------------------------------------------------------------------------
    # Chart 1: Service Type Demand & Frequency (Horizontal Bar Chart)
    # -------------------------------------------------------------------------
    plt.figure(figsize=(10, 6))
    service_counts = df['service_type'].value_counts()
    colors = sns.color_palette("viridis", len(service_counts))
    bars = plt.barh(service_counts.index, service_counts.values, color=colors, edgecolor='black', alpha=0.85)
    plt.title("Automobile Service Type Demand & Booking Frequency", fontsize=14, fontweight='bold', pad=15)
    plt.xlabel("Number of Service Appointments Logged", fontsize=12)
    plt.ylabel("Requested Service Classification", fontsize=12)
    for bar in bars:
        plt.text(bar.get_width() + 0.1, bar.get_y() + bar.get_height()/2,
                 f"{int(bar.get_width())}", va='center', ha='left', fontsize=11, fontweight='bold')
    plt.tight_layout()
    chart1_path = os.path.join(OUTPUT_DIR, "1_service_type_distribution.png")
    plt.savefig(chart1_path, dpi=300)
    plt.close()
    print(f"  [SAVED] Chart 1: {chart1_path}")

    # -------------------------------------------------------------------------
    # Chart 2: Vehicle Type vs. Maintenance Cost (Seaborn Box Plot)
    # -------------------------------------------------------------------------
    plt.figure(figsize=(10, 6))
    sns.boxplot(x='vehicle_type', y='total_amount', data=df, palette="Set2", showmeans=True,
                meanprops={"marker":"o", "markerfacecolor":"red", "markeredgecolor":"black", "markersize":"8"})
    plt.title("Maintenance Expenditure Distribution by Vehicle Type (₹)", fontsize=14, fontweight='bold', pad=15)
    plt.xlabel("Vehicle Classification", fontsize=12)
    plt.ylabel("Total Service Invoice Amount (₹)", fontsize=12)
    plt.tight_layout()
    chart2_path = os.path.join(OUTPUT_DIR, "2_cost_distribution_by_vehicle_type.png")
    plt.savefig(chart2_path, dpi=300)
    plt.close()
    print(f"  [SAVED] Chart 2: {chart2_path}")

    # -------------------------------------------------------------------------
    # Chart 3: Monthly Workload & Revenue Trend (Dual-Axis Time Series)
    # -------------------------------------------------------------------------
    monthly_data = df.groupby('service_month').agg(
        total_services=('record_id', 'count'),
        total_revenue=('total_amount', 'sum')
    ).reset_index()

    fig, ax1 = plt.subplots(figsize=(10, 6))
    color = 'tab:blue'
    ax1.set_xlabel('Service Month', fontsize=12)
    ax1.set_ylabel('Total Completed Services (Workload)', color=color, fontsize=12)
    bars = ax1.bar(monthly_data['service_month'], monthly_data['total_services'], color=color, alpha=0.6, width=0.4, label='Services Count')
    ax1.tick_params(axis='y', labelcolor=color)

    ax2 = ax1.twinx()
    color = 'tab:red'
    ax2.set_ylabel('Total Revenue Realized (₹)', color=color, fontsize=12)
    lines = ax2.plot(monthly_data['service_month'], monthly_data['total_revenue'], color=color, marker='o', linewidth=2.5, label='Revenue (₹)')
    ax2.tick_params(axis='y', labelcolor=color)

    plt.title("Service Center Monthly Workload & Revenue Evolution (Time Series)", fontsize=14, fontweight='bold', pad=15)
    fig.tight_layout()
    chart3_path = os.path.join(OUTPUT_DIR, "3_monthly_workload_and_revenue.png")
    plt.savefig(chart3_path, dpi=300)
    plt.close()
    print(f"  [SAVED] Chart 3: {chart3_path}")

    # -------------------------------------------------------------------------
    # Chart 4: Spare Parts Cost vs. Labor Charges (Scatter Plot with Regression)
    # -------------------------------------------------------------------------
    plt.figure(figsize=(10, 6))
    sns.regplot(x='parts_cost', y='labor_charges', data=df,
                scatter_kws={'alpha':0.7, 'color':'teal', 's':70},
                line_kws={'color':'crimson', 'linewidth':2})
    plt.title("Correlation Analysis: Spare Parts Expenditure vs. Labor Charges (₹)", fontsize=14, fontweight='bold', pad=15)
    plt.xlabel("Total Spare Parts Cost (₹)", fontsize=12)
    plt.ylabel("Labor & Technician Charges (₹)", fontsize=12)
    plt.tight_layout()
    chart4_path = os.path.join(OUTPUT_DIR, "4_parts_cost_vs_labor_charges.png")
    plt.savefig(chart4_path, dpi=300)
    plt.close()
    print(f"  [SAVED] Chart 4: {chart4_path}")

    # -------------------------------------------------------------------------
    # Chart 5: Correlation Heatmap of Numerical Service Metrics
    # -------------------------------------------------------------------------
    plt.figure(figsize=(9, 7))
    num_vars = ['labor_hours', 'labor_charges', 'parts_cost', 'subtotal', 'discount_amount', 'total_amount']
    corr_matrix = df[num_vars].corr()
    sns.heatmap(corr_matrix, annot=True, fmt=".2f", cmap='coolwarm', square=True,
                linewidths=1.0, cbar_kws={'shrink': 0.8})
    plt.title("Multivariate Correlation Heatmap (Service Metrics)", fontsize=14, fontweight='bold', pad=15)
    plt.tight_layout()
    chart5_path = os.path.join(OUTPUT_DIR, "5_correlation_heatmap.png")
    plt.savefig(chart5_path, dpi=300)
    plt.close()
    print(f"  [SAVED] Chart 5: {chart5_path}")

    # -------------------------------------------------------------------------
    # Chart 6: Payment Methods Breakdown (Donut Chart)
    # -------------------------------------------------------------------------
    plt.figure(figsize=(8, 8))
    pay_counts = df['payment_mode'].value_counts()
    colors = sns.color_palette("pastel")[0:len(pay_counts)]
    plt.pie(pay_counts.values, labels=pay_counts.index, autopct='%1.1f%%', startangle=140,
            colors=colors, pctdistance=0.80, explode=[0.05]*len(pay_counts))
    centre_circle = plt.Circle((0, 0), 0.60, fc='white')
    fig = plt.gcf()
    fig.gca().add_artist(centre_circle)
    plt.title("Customer Payment Channel Distribution", fontsize=14, fontweight='bold')
    plt.tight_layout()
    chart6_path = os.path.join(OUTPUT_DIR, "6_payment_distribution_donut.png")
    plt.savefig(chart6_path, dpi=300)
    plt.close()
    print(f"  [SAVED] Chart 6: {chart6_path}")

    print("\n[SUCCESS] All 6 high-resolution charts successfully exported to visualizations directory!")

if __name__ == "__main__":
    raw_df = retrieve_from_csv()
    clean_df = preprocess_vehicle_data(raw_df)
    generate_visualizations(clean_df)
