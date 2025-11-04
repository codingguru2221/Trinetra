@echo off
title Trinetra Cybersecurity Tool - Swing Version

echo ========================================
echo   TRINETRA CYBERSECURITY TOOL - SWING
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

echo Starting Trinetra Swing GUI Application...
echo.

REM Run the Swing GUI version
java -jar target\Trinetra-1.0-SNAPSHOT.jar --swing

echo.
echo Trinetra Swing GUI application closed.
pause