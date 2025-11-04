#!/bin/bash

echo "========================================"
echo "  TRINETRA CYBERSECURITY TOOL"
echo "========================================"
echo

# Check if the JAR file exists
if [ ! -f "target/Trinetra-1.0-SNAPSHOT.jar" ]; then
    echo "Error: Trinetra JAR file not found!"
    echo "Please build the project first using: mvn clean package"
    echo
    read -p "Press any key to continue..." -n1 -s
    exit 1
fi

echo "Starting Trinetra GUI Application..."
echo

# Run the JavaFX GUI version
java -jar target/Trinetra-1.0-SNAPSHOT.jar --gui

echo
echo "Trinetra GUI application closed."
read -p "Press any key to continue..." -n1 -s