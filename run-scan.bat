@echo off
echo Trinetra Cybersecurity Tool - Quick Scan
echo =======================================
echo.

echo Running vulnerability scan on http://example.com...
echo.

java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target http://example.com

echo.
echo Scan completed.
pause