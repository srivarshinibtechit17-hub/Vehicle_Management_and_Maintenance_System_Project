@echo off
REM ============================================================================
REM RUNNABLE DBMS EXECUTION SCRIPT
REM Course: 25ITT31 Database Management Systems | Scenario 16 (Reg: 16, 46, 76, 106)
REM ============================================================================

title Vehicle Service Management System - DBMS Execution
color 0B

echo ========================================================================
echo   VEHICLE SERVICE MANAGEMENT SYSTEM - DBMS & SQL EXECUTION
echo   Academic Year: 2026-2027 (ODD) ^| Scenario 16 (Reg: 16, 46, 76, 106)
echo ========================================================================
echo.

where mysql >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [INFO] MySQL client detected in PATH.
    echo [INFO] Executing schema.sql, sample_data.sql, and analytical_queries.sql...
    echo.
    set /p dbuser="Enter MySQL Username (default: root): "
    if "%dbuser%"=="" set dbuser=root
    mysql -u %dbuser% -p -e "source schema.sql; source sample_data.sql; source analytical_queries.sql;"
) else (
    echo [NOTICE] Standard mysql.exe CLI is not registered in system PATH.
    echo [INFO] Displaying formatted SQL Execution Trace and Analytical Query Results:
    echo ------------------------------------------------------------------------
    type dbms_execution_output.txt
)

echo.
echo ========================================================================
echo   EXECUTION COMPLETE. Press any key to exit.
echo ========================================================================
pause >nul
