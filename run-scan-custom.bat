@echo off
setlocal

echo Trinetra Cybersecurity Tool - Custom Scan
echo =======================================
echo.

if "%1"=="" (
    echo Usage: run-scan-custom.bat [target_url]
    echo Example: run-scan-custom.bat http://example.com
    echo.
    pause
    exit /b
)

echo Running vulnerability scan on %1...
echo.

java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target %1

echo.
echo Scan completed.
pause