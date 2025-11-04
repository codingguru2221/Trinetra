package com.trinetra.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import com.trinetra.core.TrinetraEngine;

public class TrinetraGUI extends Application {
    
    private TextArea outputArea;
    private TextField targetField;
    private ComboBox<String> operationCombo;
    private ComboBox<String> scanTypeCombo;
    
    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        
        // Create title
        Label titleLabel = new Label("Trinetra Cybersecurity Tool");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        // Create shadow effect for title
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.GRAY);
        titleLabel.setEffect(shadow);
        
        // Create input section
        VBox inputSection = new VBox(10);
        inputSection.setPadding(new Insets(10));
        inputSection.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), Insets.EMPTY)));
        
        // Target URL input
        HBox targetBox = new HBox(10);
        targetBox.setAlignment(Pos.CENTER_LEFT);
        Label targetLabel = new Label("Target URL:");
        targetField = new TextField("http://example.com");
        targetField.setPrefWidth(300);
        targetBox.getChildren().addAll(targetLabel, targetField);
        
        // Operation selection
        HBox operationBox = new HBox(10);
        operationBox.setAlignment(Pos.CENTER_LEFT);
        Label operationLabel = new Label("Operation:");
        operationCombo = new ComboBox<>();
        operationCombo.getItems().addAll(
            "Scanner Attacks",
            "Analyzer Attacks",
            "Apply Defenses"
        );
        operationCombo.setValue("Scanner Attacks");
        operationBox.getChildren().addAll(operationLabel, operationCombo);
        
        // Scan type selection
        HBox scanTypeBox = new HBox(10);
        scanTypeBox.setAlignment(Pos.CENTER_LEFT);
        Label scanTypeLabel = new Label("Scan Type:");
        scanTypeCombo = new ComboBox<>();
        scanTypeCombo.getItems().addAll(
            "Port Scan",
            "Directory Enumeration",
            "SQL Injection",
            "XSS Analysis",
            "Full Scan"
        );
        scanTypeCombo.setValue("Port Scan");
        scanTypeBox.getChildren().addAll(scanTypeLabel, scanTypeCombo);
        
        // Add input components to input section
        inputSection.getChildren().addAll(targetBox, operationBox, scanTypeBox);
        
        // Create buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button executeButton = new Button("Execute");
        executeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        executeButton.setOnAction(e -> executeOperation());
        
        Button clearButton = new Button("Clear Output");
        clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        clearButton.setOnAction(e -> outputArea.clear());
        
        buttonBox.getChildren().addAll(executeButton, clearButton);
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(15);
        outputArea.setPrefColumnCount(80);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setText("Welcome to Trinetra Cybersecurity Tool!\nSelect an operation and click Execute to begin.\n");
        
        // Add all components to root
        root.getChildren().addAll(titleLabel, inputSection, buttonBox, outputArea);
        
        // Create scene and stage
        Scene scene = new Scene(root, 800, 600);
        
        // Add some CSS styling
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto");
        
        primaryStage.setTitle("Trinetra Cybersecurity Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void executeOperation() {
        String target = targetField.getText();
        String operation = operationCombo.getValue();
        String scanType = scanTypeCombo.getValue();
        
        if (target.isEmpty()) {
            showAlert("Error", "Please enter a target URL");
            return;
        }
        
        outputArea.appendText("Executing: " + operation + " on " + target + "\n");
        outputArea.appendText("Scan Type: " + scanType + "\n");
        outputArea.appendText("----------------------------------------\n");
        
        // Simulate execution (in a real app, this would call the Trinetra engine)
        simulateExecution(operation, scanType, target);
    }
    
    private void simulateExecution(String operation, String scanType, String target) {
        switch (operation) {
            case "Scanner Attacks":
                performScannerAttack(scanType, target);
                break;
            case "Analyzer Attacks":
                performAnalyzerAttack(scanType, target);
                break;
            case "Apply Defenses":
                applyDefenses();
                break;
            default:
                outputArea.appendText("Unknown operation: " + operation + "\n");
        }
    }
    
    private void performScan(String scanType, String target) {
        outputArea.appendText("Starting vulnerability scan on " + target + "...\n");
        
        switch (scanType) {
            case "Quick Scan":
                outputArea.appendText("Performing quick SQL injection scan...\n");
                outputArea.appendText("[+] Testing login form with payload: ' OR '1'='1\n");
                outputArea.appendText("[+] Testing search parameter with payload: UNION SELECT\n");
                outputArea.appendText("\n=== SCAN RESULTS ===\n");
                outputArea.appendText("Vulnerabilities Found:\n");
                outputArea.appendText("1. SQL Injection in login form (CRITICAL)\n");
                outputArea.appendText("2. Missing security headers (MEDIUM)\n");
                break;
                
            case "SQL Injection":
                outputArea.appendText("Performing detailed SQL injection scan...\n");
                outputArea.appendText("[+] Testing multiple injection points\n");
                outputArea.appendText("[+] Checking for error-based injection\n");
                outputArea.appendText("[+] Testing for blind SQL injection\n");
                outputArea.appendText("\n=== SQL INJECTION REPORT ===\n");
                outputArea.appendText("Vulnerabilities Found:\n");
                outputArea.appendText("1. SQL Injection in login form (CRITICAL)\n");
                outputArea.appendText("2. Union-based SQL Injection in search (HIGH)\n");
                outputArea.appendText("3. Blind SQL Injection in ID parameter (MEDIUM)\n");
                break;
                
            case "XSS Vulnerabilities":
                outputArea.appendText("Performing XSS vulnerability scan...\n");
                outputArea.appendText("[+] Testing search parameter with <script> payload\n");
                outputArea.appendText("[+] Testing comment form with javascript: payload\n");
                outputArea.appendText("[+] Testing URL parameters with onload= payload\n");
                outputArea.appendText("\n=== XSS VULNERABILITY REPORT ===\n");
                outputArea.appendText("Vulnerabilities Found:\n");
                outputArea.appendText("1. Reflected XSS in search parameter (HIGH)\n");
                outputArea.appendText("2. Stored XSS in comment functionality (CRITICAL)\n");
                break;
                
            case "Full Scan":
                outputArea.appendText("Performing full vulnerability scan...\n");
                outputArea.appendText("[+] Scanning open ports\n");
                outputArea.appendText("[+] Testing for SQL injection\n");
                outputArea.appendText("[+] Testing for XSS vulnerabilities\n");
                outputArea.appendText("[+] Checking security headers\n");
                outputArea.appendText("\n=== FULL SCAN REPORT ===\n");
                outputArea.appendText("CRITICAL Vulnerabilities:\n");
                outputArea.appendText("1. SQL Injection vulnerability in login form\n");
                outputArea.appendText("2. Cross-site scripting (XSS) in search functionality\n");
                outputArea.appendText("\nHIGH Vulnerabilities:\n");
                outputArea.appendText("1. Missing security headers (HSTS, CSP)\n");
                outputArea.appendText("2. Open MySQL port (3306) accessible from public\n");
                break;
        }
        
        outputArea.appendText("\nScan completed successfully.\n");
        outputArea.appendText("----------------------------------------\n");
    }
    
    private void performAnalysis(String target) {
        outputArea.appendText("Analyzing threats from " + target + "...\n");
        outputArea.appendText("[+] Parsing log data\n");
        outputArea.appendText("[+] Identifying suspicious patterns\n");
        outputArea.appendText("[+] Cross-referencing with threat intelligence\n");
        outputArea.appendText("\n=== THREAT ANALYSIS REPORT ===\n");
        outputArea.appendText("CRITICAL Threats:\n");
        outputArea.appendText("1. Possible brute force attack detected from 3 IPs\n");
        outputArea.appendText("2. Suspicious file upload attempt identified\n");
        outputArea.appendText("\nHIGH Threats:\n");
        outputArea.appendText("1. Multiple failed login attempts from foreign IPs\n");
        outputArea.appendText("2. Potential DDoS pattern detected\n");
        outputArea.appendText("\nAnalysis completed successfully.\n");
        outputArea.appendText("----------------------------------------\n");
    }
    
    private void applyDefenses() {
        outputArea.appendText("Applying defense mechanisms...\n");
        outputArea.appendText("[+] Enabling firewall protection\n");
        outputArea.appendText("[+] Hardening system security\n");
        outputArea.appendText("[+] Applying security configuration\n");
        outputArea.appendText("\n=== DEFENSE REPORT ===\n");
        outputArea.appendText("DEFENSE STATUS: ACTIVE\n");
        outputArea.appendText("\nPROTECTIONS ENABLED:\n");
        outputArea.appendText("1. Firewall protection\n");
        outputArea.appendText("2. System hardening\n");
        outputArea.appendText("3. Security configuration\n");
        outputArea.appendText("\nDefenses applied successfully.\n");
        outputArea.appendText("----------------------------------------\n");
    }
    
    private void performScannerAttack(String attackType, String target) {
        outputArea.appendText("Executing Scanner Attack: " + attackType + " on " + target + "\n");
        
        switch (attackType) {
            case "Port Scan":
                outputArea.appendText("Scanner Attack: Port Scan\n");
                outputArea.appendText("Description: Scans open ports on target system\n");
                outputArea.appendText("Severity: 6/10\n");
                outputArea.appendText("[+] Scanning ports on " + target + "...\n");
                outputArea.appendText("Port 21: CLOSED\n");
                outputArea.appendText("Port 22: OPEN\n");
                outputArea.appendText("Port 23: CLOSED\n");
                outputArea.appendText("Port 25: CLOSED\n");
                outputArea.appendText("Port 53: OPEN\n");
                outputArea.appendText("Port 80: OPEN\n");
                outputArea.appendText("Port 110: CLOSED\n");
                outputArea.appendText("Port 443: OPEN\n");
                outputArea.appendText("Port 3306: OPEN\n");
                outputArea.appendText("Port 5432: CLOSED\n");
                outputArea.appendText("Port scan completed.\n");
                break;
                
            case "Directory Enumeration":
                outputArea.appendText("Scanner Attack: Directory Enumeration\n");
                outputArea.appendText("Description: Finds hidden directories and files\n");
                outputArea.appendText("Severity: 5/10\n");
                outputArea.appendText("[+] Enumerating directories on " + target + "...\n");
                outputArea.appendText("Found: " + target + "/admin\n");
                outputArea.appendText("Found: " + target + "/backup\n");
                outputArea.appendText("Found: " + target + "/api\n");
                outputArea.appendText("Directory enumeration completed.\n");
                break;
                
            case "Full Scan":
                outputArea.appendText("Scanner Attack: Full Scan\n");
                outputArea.appendText("Description: Comprehensive scanning of target\n");
                outputArea.appendText("Severity: 7/10\n");
                outputArea.appendText("[+] Scanning ports on " + target + "...\n");
                outputArea.appendText("Port 22: OPEN\n");
                outputArea.appendText("Port 80: OPEN\n");
                outputArea.appendText("Port 443: OPEN\n");
                outputArea.appendText("[+] Enumerating directories...\n");
                outputArea.appendText("Found: " + target + "/admin\n");
                outputArea.appendText("Found: " + target + "/api\n");
                outputArea.appendText("Full scan completed.\n");
                break;
                
            default:
                outputArea.appendText("Unknown scanner attack type: " + attackType + "\n");
        }
        
        outputArea.appendText("\nScanner attack completed successfully.\n");
        outputArea.appendText("----------------------------------------\n");
    }
    
    private void performAnalyzerAttack(String attackType, String target) {
        outputArea.appendText("Executing Analyzer Attack: " + attackType + " on " + target + "\n");
        
        switch (attackType) {
            case "SQL Injection":
                outputArea.appendText("Analyzer Attack: SQL Injection\n");
                outputArea.appendText("Description: Tests for SQL injection vulnerabilities\n");
                outputArea.appendText("Severity: 9/10\n");
                outputArea.appendText("[+] Testing for SQL injection on " + target + "...\n");
                outputArea.appendText("VULNERABLE: Payload '' OR '1'='1' was successful\n");
                outputArea.appendText("VULNERABLE: Payload '; DROP TABLE users; --' was successful\n");
                outputArea.appendText("SQL injection analysis completed.\n");
                break;
                
            case "XSS Analysis":
                outputArea.appendText("Analyzer Attack: XSS Analysis\n");
                outputArea.appendText("Description: Tests for Cross-Site Scripting vulnerabilities\n");
                outputArea.appendText("Severity: 7/10\n");
                outputArea.appendText("[+] Testing for XSS on " + target + "...\n");
                outputArea.appendText("VULNERABLE: Payload '<script>alert('XSS')</script>' was successful\n");
                outputArea.appendText("VULNERABLE: Payload 'javascript:alert('XSS')' was successful\n");
                outputArea.appendText("XSS analysis completed.\n");
                break;
                
            default:
                outputArea.appendText("Unknown analyzer attack type: " + attackType + "\n");
        }
        
        outputArea.appendText("\nAnalyzer attack completed successfully.\n");
        outputArea.appendText("----------------------------------------\n");
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}