@echo off
REM ============================================================================
REM RUNNABLE FOUNDATIONS OF DATA SCIENCE EXECUTION SCRIPT
REM Course: 25ITC31 Foundations of Data Science | Scenario 16 (Reg: 16, 46, 76, 106)
REM ============================================================================

title Vehicle Service Management System - Data Science Pipeline
color 0E

echo ========================================================================
echo   VEHICLE SERVICE MANAGEMENT SYSTEM - DATA SCIENCE & VISUALIZATIONS
echo   Academic Year: 2026-2027 (ODD) ^| Scenario 16 (Reg: 16, 46, 76, 106)
echo ========================================================================
echo.

where python >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [INFO] Python runtime detected.
    echo [STEP 1] Running Data Preprocessing & Wrangling...
    python data_preprocessing.py
    echo.
    echo [STEP 2] Generating EDA Visualizations & Charts...
    python data_visualization.py
    echo.
    echo [INFO] Opening visualizations directory...
    start visualizations
) else (
    echo [NOTICE] Python executable is not registered in system PATH.
    echo [INFO] Displaying formatted Data Science Preprocessing Trace & Output:
    echo ------------------------------------------------------------------------
    type fds_execution_output.txt
    echo.
    echo [INFO] Opening pre-generated charts in visualizations directory...
    start visualizations
)

echo.
echo ========================================================================
echo   EXECUTION COMPLETE. Press any key to exit.
echo ========================================================================
pause >nul
