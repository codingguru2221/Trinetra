package com.trinetra.attacks;

/**
 * Base class for Scanner Attacks
 * These attacks scan, probe, or crawl the web app to find weaknesses
 */
public abstract class ScannerAttack {
    
    protected String name;
    protected String description;
    protected int severity; // 1-10 scale
    
    public ScannerAttack(String name, String description, int severity) {
        this.name = name;
        this.description = description;
        this.severity = severity;
    }
    
    /**
     * Execute the scanner attack
     */
    public abstract void execute();
    
    /**
     * Get attack details
     */
    public String getDetails() {
        return String.format("Scanner Attack: %s\nDescription: %s\nSeverity: %d/10", 
                            name, description, severity);
    }
    
    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSeverity() { return severity; }
}