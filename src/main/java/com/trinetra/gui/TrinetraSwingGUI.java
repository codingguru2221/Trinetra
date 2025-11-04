package com.trinetra.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrinetraSwingGUI extends JFrame {
    
    private JTextArea outputArea;
    private JTextField targetField;
    private JComboBox<String> operationCombo;
    private JComboBox<String> scanTypeCombo;
    
    public TrinetraSwingGUI() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Trinetra Cybersecurity Tool - Swing Version");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window
    }
    
    private void initializeComponents() {
        // Create components
        outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setText("Welcome to Trinetra Cybersecurity Tool!\nSelect an operation and click Execute to begin.\n");
        
        targetField = new JTextField("http://example.com", 30);
        
        operationCombo = new JComboBox<>(new String[]{
            "Scanner Attacks",
            "Analyzer Attacks",
            "Apply Defenses"
        });
        
        scanTypeCombo = new JComboBox<>(new String[]{
            "Port Scan",
            "Directory Enumeration",
            "SQL Injection",
            "XSS Analysis",
            "Full Scan"
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Trinetra Cybersecurity Tool");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Target URL row
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Target URL:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        inputPanel.add(targetField, gbc);
        
        // Operation row
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(new JLabel("Operation:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        inputPanel.add(operationCombo, gbc);
        
        // Scan Type row
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(new JLabel("Scan Type:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        inputPanel.add(scanTypeCombo, gbc);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton executeButton = new JButton("Execute");
        executeButton.setBackground(Color.GREEN);
        JButton clearButton = new JButton("Clear Output");
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);
        
        // Add event handlers
        executeButton.addActionListener(new ExecuteButtonListener());
        clearButton.addActionListener(e -> outputArea.setText(""));
        
        // Create output panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        // Create center panel to hold input and output
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(outputPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Event handlers are set up in setupLayout method
    }
    
    private class ExecuteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String target = targetField.getText();
            String operation = (String) operationCombo.getSelectedItem();
            String scanType = (String) scanTypeCombo.getSelectedItem();
            
            if (target.isEmpty()) {
                JOptionPane.showMessageDialog(TrinetraSwingGUI.this, 
                    "Please enter a target URL", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            outputArea.append("Executing: " + operation + " on " + target + "\n");
            outputArea.append("Scan Type: " + scanType + "\n");
            outputArea.append("----------------------------------------\n");
            
            // Simulate execution
            simulateExecution(operation, scanType, target);
        }
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
                outputArea.append("Unknown operation: " + operation + "\n");
        }
    }
    
    private void performScan(String scanType, String target) {
        outputArea.append("Starting vulnerability scan on " + target + "...\n");
        
        switch (scanType) {
            case "Quick Scan":
                outputArea.append("Performing quick SQL injection scan...\n");
                outputArea.append("[+] Testing login form with payload: ' OR '1'='1\n");
                outputArea.append("[+] Testing search parameter with payload: UNION SELECT\n");
                outputArea.append("\n=== SCAN RESULTS ===\n");
                outputArea.append("Vulnerabilities Found:\n");
                outputArea.append("1. SQL Injection in login form (CRITICAL)\n");
                outputArea.append("2. Missing security headers (MEDIUM)\n");
                break;
                
            case "SQL Injection":
                outputArea.append("Performing detailed SQL injection scan...\n");
                outputArea.append("[+] Testing multiple injection points\n");
                outputArea.append("[+] Checking for error-based injection\n");
                outputArea.append("[+] Testing for blind SQL injection\n");
                outputArea.append("\n=== SQL INJECTION REPORT ===\n");
                outputArea.append("Vulnerabilities Found:\n");
                outputArea.append("1. SQL Injection in login form (CRITICAL)\n");
                outputArea.append("2. Union-based SQL Injection in search (HIGH)\n");
                outputArea.append("3. Blind SQL Injection in ID parameter (MEDIUM)\n");
                break;
                
            case "XSS Vulnerabilities":
                outputArea.append("Performing XSS vulnerability scan...\n");
                outputArea.append("[+] Testing search parameter with <script> payload\n");
                outputArea.append("[+] Testing comment form with javascript: payload\n");
                outputArea.append("[+] Testing URL parameters with onload= payload\n");
                outputArea.append("\n=== XSS VULNERABILITY REPORT ===\n");
                outputArea.append("Vulnerabilities Found:\n");
                outputArea.append("1. Reflected XSS in search parameter (HIGH)\n");
                outputArea.append("2. Stored XSS in comment functionality (CRITICAL)\n");
                break;
                
            case "Full Scan":
                outputArea.append("Performing full vulnerability scan...\n");
                outputArea.append("[+] Scanning open ports\n");
                outputArea.append("[+] Testing for SQL injection\n");
                outputArea.append("[+] Testing for XSS vulnerabilities\n");
                outputArea.append("[+] Checking security headers\n");
                outputArea.append("\n=== FULL SCAN REPORT ===\n");
                outputArea.append("CRITICAL Vulnerabilities:\n");
                outputArea.append("1. SQL Injection vulnerability in login form\n");
                outputArea.append("2. Cross-site scripting (XSS) in search functionality\n");
                outputArea.append("\nHIGH Vulnerabilities:\n");
                outputArea.append("1. Missing security headers (HSTS, CSP)\n");
                outputArea.append("2. Open MySQL port (3306) accessible from public\n");
                break;
        }
        
        outputArea.append("\nScan completed successfully.\n");
        outputArea.append("----------------------------------------\n");
    }
    
    private void performAnalysis(String target) {
        outputArea.append("Analyzing threats from " + target + "...\n");
        outputArea.append("[+] Parsing log data\n");
        outputArea.append("[+] Identifying suspicious patterns\n");
        outputArea.append("[+] Cross-referencing with threat intelligence\n");
        outputArea.append("\n=== THREAT ANALYSIS REPORT ===\n");
        outputArea.append("CRITICAL Threats:\n");
        outputArea.append("1. Possible brute force attack detected from 3 IPs\n");
        outputArea.append("2. Suspicious file upload attempt identified\n");
        outputArea.append("\nHIGH Threats:\n");
        outputArea.append("1. Multiple failed login attempts from foreign IPs\n");
        outputArea.append("2. Potential DDoS pattern detected\n");
        outputArea.append("\nAnalysis completed successfully.\n");
        outputArea.append("----------------------------------------\n");
    }
    
    private void applyDefenses() {
        outputArea.append("Applying defense mechanisms...\n");
        outputArea.append("[+] Enabling firewall protection\n");
        outputArea.append("[+] Hardening system security\n");
        outputArea.append("[+] Applying security configuration\n");
        outputArea.append("\n=== DEFENSE REPORT ===\n");
        outputArea.append("DEFENSE STATUS: ACTIVE\n");
        outputArea.append("\nPROTECTIONS ENABLED:\n");
        outputArea.append("1. Firewall protection\n");
        outputArea.append("2. System hardening\n");
        outputArea.append("3. Security configuration\n");
        outputArea.append("\nDefenses applied successfully.\n");
        outputArea.append("----------------------------------------\n");
    }
    
    private void performScannerAttack(String attackType, String target) {
        outputArea.append("Executing Scanner Attack: " + attackType + " on " + target + "\n");
        
        switch (attackType) {
            case "Port Scan":
                outputArea.append("Scanner Attack: Port Scan\n");
                outputArea.append("Description: Scans open ports on target system\n");
                outputArea.append("Severity: 6/10\n");
                outputArea.append("[+] Scanning ports on " + target + "...\n");
                outputArea.append("Port 21: CLOSED\n");
                outputArea.append("Port 22: OPEN\n");
                outputArea.append("Port 23: CLOSED\n");
                outputArea.append("Port 25: CLOSED\n");
                outputArea.append("Port 53: OPEN\n");
                outputArea.append("Port 80: OPEN\n");
                outputArea.append("Port 110: CLOSED\n");
                outputArea.append("Port 443: OPEN\n");
                outputArea.append("Port 3306: OPEN\n");
                outputArea.append("Port 5432: CLOSED\n");
                outputArea.append("Port scan completed.\n");
                break;
                
            case "Directory Enumeration":
                outputArea.append("Scanner Attack: Directory Enumeration\n");
                outputArea.append("Description: Finds hidden directories and files\n");
                outputArea.append("Severity: 5/10\n");
                outputArea.append("[+] Enumerating directories on " + target + "...\n");
                outputArea.append("Found: " + target + "/admin\n");
                outputArea.append("Found: " + target + "/backup\n");
                outputArea.append("Found: " + target + "/api\n");
                outputArea.append("Directory enumeration completed.\n");
                break;
                
            case "Full Scan":
                outputArea.append("Scanner Attack: Full Scan\n");
                outputArea.append("Description: Comprehensive scanning of target\n");
                outputArea.append("Severity: 7/10\n");
                outputArea.append("[+] Scanning ports on " + target + "...\n");
                outputArea.append("Port 22: OPEN\n");
                outputArea.append("Port 80: OPEN\n");
                outputArea.append("Port 443: OPEN\n");
                outputArea.append("[+] Enumerating directories...\n");
                outputArea.append("Found: " + target + "/admin\n");
                outputArea.append("Found: " + target + "/api\n");
                outputArea.append("Full scan completed.\n");
                break;
                
            default:
                outputArea.append("Unknown scanner attack type: " + attackType + "\n");
        }
        
        outputArea.append("\nScanner attack completed successfully.\n");
        outputArea.append("----------------------------------------\n");
    }
    
    private void performAnalyzerAttack(String attackType, String target) {
        outputArea.append("Executing Analyzer Attack: " + attackType + " on " + target + "\n");
        
        switch (attackType) {
            case "SQL Injection":
                outputArea.append("Analyzer Attack: SQL Injection\n");
                outputArea.append("Description: Tests for SQL injection vulnerabilities\n");
                outputArea.append("Severity: 9/10\n");
                outputArea.append("[+] Testing for SQL injection on " + target + "...\n");
                outputArea.append("VULNERABLE: Payload '' OR '1'='1' was successful\n");
                outputArea.append("VULNERABLE: Payload '; DROP TABLE users; --' was successful\n");
                outputArea.append("SQL injection analysis completed.\n");
                break;
                
            case "XSS Analysis":
                outputArea.append("Analyzer Attack: XSS Analysis\n");
                outputArea.append("Description: Tests for Cross-Site Scripting vulnerabilities\n");
                outputArea.append("Severity: 7/10\n");
                outputArea.append("[+] Testing for XSS on " + target + "...\n");
                outputArea.append("VULNERABLE: Payload '<script>alert('XSS')</script>' was successful\n");
                outputArea.append("VULNERABLE: Payload 'javascript:alert('XSS')' was successful\n");
                outputArea.append("XSS analysis completed.\n");
                break;
                
            default:
                outputArea.append("Unknown analyzer attack type: " + attackType + "\n");
        }
        
        outputArea.append("\nAnalyzer attack completed successfully.\n");
        outputArea.append("----------------------------------------\n");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TrinetraSwingGUI().setVisible(true);
        });
    }
}