@echo off
REM ============================================================================
REM RUNNABLE JAVA EXECUTION SCRIPT
REM Course: 25ITT32 Java Programming | Scenario 16 (Reg: 16, 46, 76, 106)
REM ============================================================================

title Vehicle Service Management System - Java Execution
color 0A

echo ========================================================================
echo   VEHICLE SERVICE MANAGEMENT SYSTEM - JAVA PROGRAMMING CORE
echo   Academic Year: 2026-2027 (ODD) ^| Scenario 16 (Reg: 16, 46, 76, 106)
echo ========================================================================
echo.

where javac >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [INFO] JDK Compiler detected.
    echo [INFO] Compiling Java classes into bin/...
    if not exist "bin" mkdir "bin"
    javac -d bin src\com\vehicleservice\model\*.java src\com\vehicleservice\exception\*.java src\com\vehicleservice\dao\*.java src\com\vehicleservice\service\*.java src\com\vehicleservice\thread\*.java src\com\vehicleservice\util\*.java src\com\vehicleservice\Main.java
    
    if %ERRORLEVEL% EQU 0 (
        echo [SUCCESS] Compilation completed successfully!
        echo.
        echo Select Execution Mode:
        echo   1. Automated Demonstration Mode (--demo)
        echo   2. Interactive CLI Navigation Menu
        echo.
        set /p mode="Enter choice (1 or 2, default is 1): "
        if "%mode%"=="2" (
            java -cp bin com.vehicleservice.Main
        ) else (
            java -cp bin com.vehicleservice.Main --demo
        )
    ) else (
        echo [ERROR] Java compilation encountered an error.
    )
) else (
    echo [NOTICE] Java Development Kit (javac/java) is not in system PATH.
    echo [INFO] Displaying formatted Application Execution Trace & Output:
    echo ------------------------------------------------------------------------
    type java_execution_output.txt
)

echo.
echo ========================================================================
echo   EXECUTION COMPLETE. Press any key to exit.
echo ========================================================================
pause >nul
