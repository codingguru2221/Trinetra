package com.trinetra.core;

import com.trinetra.utils.Logger;
import com.trinetra.scanner.VulnerabilityScanner;
import com.trinetra.analyzer.ThreatAnalyzer;
import com.trinetra.defense.DefenseMechanism;

import java.util.Arrays;

/**
 * Core engine for the Trinetra cybersecurity tool
 */
public class TrinetraEngine {
    
    private VulnerabilityScanner scanner;
    private ThreatAnalyzer analyzer;
    private DefenseMechanism defense;
    
    public TrinetraEngine() {
        // Initialize components
    }
    
    public void initialize() {
        Logger.info("Initializing Trinetra Engine...");
        scanner = new VulnerabilityScanner();
        analyzer = new ThreatAnalyzer();
        defense = new DefenseMechanism();
        Logger.info("Trinetra Engine initialized successfully.");
    }
    
    public void showHelp() {
        System.out.println("Trinetra Cybersecurity Tool - Help");
        System.out.println("==================================");
        System.out.println("Usage: java -jar Trinetra.jar [command] [options]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  scan     - Scan for vulnerabilities");
        System.out.println("  analyze  - Analyze potential threats");
        System.out.println("  defend   - Apply defense mechanisms");
        System.out.println("  help     - Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar Trinetra.jar scan --target http://example.com");
        System.out.println("  java -jar Trinetra.jar analyze --file report.txt");
    }
    
    public void processCommand(String[] args) {
        String command = args[0].toLowerCase();
        
        switch (command) {
            case "scan":
                handleScanCommand(Arrays.copyOfRange(args, 1, args.length));
                break;
            case "analyze":
                handleAnalyzeCommand(Arrays.copyOfRange(args, 1, args.length));
                break;
            case "defend":
                handleDefendCommand(Arrays.copyOfRange(args, 1, args.length));
                break;
            case "help":
                showHelp();
                break;
            default:
                Logger.error("Unknown command: " + command);
                showHelp();
        }
    }
    
    private void handleScanCommand(String[] args) {
        Logger.info("Executing vulnerability scan...");
        scanner.performScan(args);
    }
    
    private void handleAnalyzeCommand(String[] args) {
        Logger.info("Analyzing potential threats...");
        analyzer.analyzeThreats(args);
    }
    
    private void handleDefendCommand(String[] args) {
        Logger.info("Applying defense mechanisms...");
        defense.applyDefense(args);
    }
}