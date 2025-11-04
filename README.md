# Trinetra - Advanced Cybersecurity Tool

Trinetra is an advanced Java-based cybersecurity tool built to detect vulnerabilities, analyze web threats, and strengthen digital defenses. Inspired by Lord Shiva's Third Eye — the Trinetra, it represents divine perception and unmatched awareness in the cyber realm.

## Mission

Trinetra's mission is simple yet powerful: to help cybersecurity experts uncover hidden weaknesses, identify potential exploits, and defend systems before they're breached.

## Features

- **Vulnerability Scanning**: Detect common security issues including SQL injection, XSS, and misconfigurations
- **Threat Analysis**: Identify potential threats from log files and network traffic
- **Defense Mechanisms**: Apply security measures to strengthen system defenses
- **Scanner Attacks**: Discovery and mapping attacks (Port scanning, Directory enumeration, etc.)
- **Analyzer Attacks**: Exploitation and manipulation attacks (SQL injection, XSS, etc.)
- **Modular Architecture**: Extensible design with separate modules for scanning, analysis, and defense

## Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

## Building the Project

```bash
mvn clean package
```

This will create an executable JAR file in the `target` directory.

## Usage

```bash
java -jar target/Trinetra-1.0-SNAPSHOT.jar [command] [options]
```

### Commands

- `scan` - Scan for vulnerabilities
- `analyze` - Analyze potential threats
- `defend` - Apply defense mechanisms
- `help` - Show help message

### Examples

```bash
# Show help
java -jar target/Trinetra-1.0-SNAPSHOT.jar help

# Scan a target for SQL injection vulnerabilities
java -jar target/Trinetra-1.0-SNAPSHOT.jar scan --target http://example.com --type sql

# Scan a target for open ports
java -jar target/Trinetra-1.0-SNAPSHOT.jar scan --target http://example.com --type portscan

# Analyze a target for SQL injection vulnerabilities
java -jar target/Trinetra-1.0-SNAPSHOT.jar analyze --target http://example.com/login --type sqli

# Analyze threats from a log file
java -jar target/Trinetra-1.0-SNAPSHOT.jar analyze --file security.log --type network

# Apply firewall defense mechanisms
java -jar target/Trinetra-1.0-SNAPSHOT.jar defend --firewall --type advanced
```

## Modules

### 1. Vulnerability Scanner (`com.trinetra.scanner`)
Detects common security vulnerabilities in web applications and systems:
- SQL Injection scanning
- Cross-site Scripting (XSS) detection
- Security header analysis
- Port scanning simulation

### 2. Threat Analyzer (`com.trinetra.analyzer`)
Analyzes potential threats from various sources:
- Log file analysis
- Network traffic pattern recognition
- Behavioral analysis
- Malware detection

### 3. Attack Modules (`com.trinetra.attacks`)
Implementation of specific attack types:
- **Scanner Attacks**: Discovery and mapping attacks
  - PortScanner: Scans open ports on target systems
  - DirectoryEnumerator: Finds hidden directories and files
- **Analyzer Attacks**: Exploitation and manipulation attacks
  - SQLInjectionAnalyzer: Tests for SQL injection vulnerabilities
  - XSSAnalyzer: Tests for Cross-Site Scripting vulnerabilities

### 4. Defense Mechanism (`com.trinetra.defense`)
Applies security measures to strengthen defenses:
- Firewall configuration
- System hardening
- Security policy implementation
- Advanced threat protection

## Testing

The project includes comprehensive unit tests for all components:

```bash
mvn test
```

## Dependencies

- JUnit 4.13.2 (Testing)
- Apache Commons Lang 3.12.0 (Utility functions)
- Google Gson 2.8.9 (JSON processing)
- SLF4J 1.7.32 (Logging)
- Apache HttpComponents 4.5.13 (HTTP client)

## Architecture

Trinetra follows a modular architecture with clearly separated concerns:

```
com.trinetra
├── App (Main entry point)
├── core (Core engine)
├── scanner (Vulnerability scanning)
├── analyzer (Threat analysis)
├── defense (Defense mechanisms)
├── attacks (Specific attack implementations)
│   ├── scanner (Scanner attack types)
│   └── analyzer (Analyzer attack types)
└── utils (Utility classes)
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Inspired by Lord Shiva's Trinetra (Third Eye) representing divine perception and awareness