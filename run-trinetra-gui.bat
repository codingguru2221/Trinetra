@echo off
title Trinetra Cybersecurity Tool - GUI Version

echo ========================================
echo   TRINETRA CYBERSECURITY TOOL
echo ========================================
echo.

REM Check if the JAR file exists
if not exist "target\Trinetra-1.0-SNAPSHOT.jar" (
    echo Error: Trinetra JAR file not found!
    echo Please build the project first using: mvn clean package
    echo.
    pause
    exit /b 1
)

echo Starting Trinetra GUI Application...
echo.
echo Select GUI version:
echo 1. Classic JavaFX GUI
echo 2. Swing GUI
echo 3. Modern JavaFX GUI
echo.

choice /c 123 /m "Select GUI version"

if errorlevel 3 (
    REM Run the Modern JavaFX GUI version
    java -jar target\Trinetra-1.0-SNAPSHOT.jar --modern
) else if errorlevel 2 (
    REM Run the Swing GUI version
    java -jar target\Trinetra-1.0-SNAPSHOT.jar --swing
) else (
    REM Run the classic JavaFX GUI version
    java -jar target\Trinetra-1.0-SNAPSHOT.jar --gui
)

echo.
echo Trinetra GUI application closed.
pause