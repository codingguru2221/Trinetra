@echo off
title Trinetra Cybersecurity Tool - Interactive Mode

:menu
cls
echo ==================================================
echo           TRINETRA CYBERSECURITY TOOL
echo ==================================================
echo.
echo Select an operation to perform:
echo.
echo 1. Scan for vulnerabilities (Quick Scan)
echo 2. Scan for SQL injection vulnerabilities
echo 3. Scan for XSS vulnerabilities
echo 4. Full vulnerability scan with details
echo 5. Analyze threats from log file
echo 6. Apply firewall defense
echo 7. Harden system security
echo 8. Apply all defenses
echo 9. Exit
echo.
set /p choice="Enter your choice (1-9): "

echo.
if "%choice%"=="1" goto scan_quick
if "%choice%"=="2" goto scan_sql
if "%choice%"=="3" goto scan_xss
if "%choice%"=="4" goto scan_full
if "%choice%"=="5" goto analyze_threats
if "%choice%"=="6" goto defend_firewall
if "%choice%"=="7" goto defend_harden
if "%choice%"=="8" goto defend_all
if "%choice%"=="9" goto exit
echo Invalid choice. Please enter a number between 1-9.
pause
goto menu

:scan_quick
set /p target="Enter target URL (e.g., http://example.com): "
if "%target%"=="" (
    echo No target specified. Using default target.
    set target=http://example.com
)
echo Running quick vulnerability scan on %target%...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target %target% --type sql
echo.
echo Scan completed.
pause
goto menu

:scan_sql
set /p target="Enter target URL (e.g., http://example.com): "
if "%target%"=="" (
    echo No target specified. Using default target.
    set target=http://example.com
)
echo Running SQL injection scan on %target%...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target %target% --type sql
echo.
echo SQL injection scan completed.
pause
goto menu

:scan_xss
set /p target="Enter target URL (e.g., http://example.com): "
if "%target%"=="" (
    echo No target specified. Using default target.
    set target=http://example.com
)
echo Running XSS vulnerability scan on %target%...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target %target% --type xss
echo.
echo XSS vulnerability scan completed.
pause
goto menu

:scan_full
set /p target="Enter target URL (e.g., http://example.com): "
if "%target%"=="" (
    echo No target specified. Using default target.
    set target=http://example.com
)
echo Running full vulnerability scan on %target%...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target %target% --verbose
echo.
echo Full vulnerability scan completed.
pause
goto menu

:analyze_threats
set /p logfile="Enter log file path (or press Enter for default 'security.log'): "
if "%logfile%"=="" set logfile=security.log
echo Analyzing threats from %logfile%...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar analyze --file %logfile%
echo.
echo Threat analysis completed.
pause
goto menu

:defend_firewall
echo Applying firewall defense...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar defend --firewall
echo.
echo Firewall defense applied.
pause
goto menu

:defend_harden
echo Hardening system security...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar defend --harden
echo.
echo System hardening completed.
pause
goto menu

:defend_all
echo Applying all defense mechanisms...
echo.
java -jar target\Trinetra-1.0-SNAPSHOT.jar defend --firewall --harden --type advanced
echo.
echo All defense mechanisms applied.
pause
goto menu

:exit
echo Thank you for using Trinetra Cybersecurity Tool!
echo.
pause
exit