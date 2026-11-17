@echo off
REM ============================================================================
REM BUILD AND EXECUTION SCRIPT FOR VEHICLE SERVICE SYSTEM (JAVA)
REM Course: 25ITT32 Java Programming | Scenario 16 (Reg: 16, 46, 76, 106)
REM ============================================================================

echo [INFO] Creating bin directory...
if not exist "bin" mkdir "bin"

echo [INFO] Compiling Java source files...
javac -d bin src\com\vehicleservice\model\*.java src\com\vehicleservice\exception\*.java src\com\vehicleservice\dao\*.java src\com\vehicleservice\service\*.java src\com\vehicleservice\thread\*.java src\com\vehicleservice\util\*.java src\com\vehicleservice\Main.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java compilation failed! Please ensure JDK is installed and in your PATH.
    pause
    exit /b %ERRORLEVEL%
)

echo [SUCCESS] Compilation successful!
echo.
echo Select Mode to Run:
echo   1. Interactive CLI Mode
echo   2. Automated Demonstration Mode (--demo)
echo.
set /p choice="Enter choice (1 or 2): "

if "%choice%"=="2" (
    echo [INFO] Running Automated Demonstration...
    java -cp bin com.vehicleservice.Main --demo
) else (
    echo [INFO] Launching Interactive CLI Menu...
    java -cp bin com.vehicleservice.Main
)

pause
