#!/bin/bash

echo "Trinetra Cybersecurity Tool"
echo "=========================="
echo

# Check if the JAR file exists
if [ ! -f "target/Trinetra-1.0-SNAPSHOT.jar" ]; then
    echo "Error: Trinetra JAR file not found!"
    echo "Please build the project first using: mvn clean package"
    echo
    read -p "Press any key to continue..." -n1 -s
    exit 1
fi

# Run the Trinetra application with provided arguments
java -jar target/Trinetra-1.0-SNAPSHOT.jar "$@"

# If no arguments were provided, show a hint
if [ $# -eq 0 ]; then
    echo
    echo "Tip: Use 'help' command to see available options"
    echo "Example: ./run-trinetra.sh help"
fi