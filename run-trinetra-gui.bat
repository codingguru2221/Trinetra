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

REM Run the JavaFX GUI version
java -jar target\Trinetra-1.0-SNAPSHOT.jar --gui

echo.
echo Trinetra GUI application closed.
pause