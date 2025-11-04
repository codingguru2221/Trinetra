@echo off
echo Trinetra Cybersecurity Tool - Detailed Scan
echo =========================================
echo.

echo Running detailed vulnerability scan on http://youtube.com...
echo.

java -jar target\Trinetra-1.0-SNAPSHOT.jar scan --target http://example.com --verbose

echo.
echo Detailed scan completed.
pause