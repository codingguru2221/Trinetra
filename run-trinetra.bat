@echo off
echo Trinetra Cybersecurity Tool
echo ==========================
echo.

REM Check if the JAR file exists
if not exist "target\Trinetra-1.0-SNAPSHOT.jar" (
    echo Error: Trinetra JAR file not found!
    echo Please build the project first using: mvn clean package
    echo.
    pause
    exit /b 1
)

REM Run the Trinetra application with provided arguments
java -jar target\Trinetra-1.0-SNAPSHOT.jar %*

REM If no arguments were provided, show a hint
if "%1"=="" (
    echo.
    echo Tip: Use 'help' command to see available options
    echo Example: run-trinetra.bat help
)