package com.trinetra.attacks;

/**
 * Base class for Analyzer Attacks
 * These attacks analyze code, traffic, logic, or behavior to exploit vulnerabilities
 */
public abstract class AnalyzerAttack {
    
    protected String name;
    protected String description;
    protected int severity; // 1-10 scale
    
    public AnalyzerAttack(String name, String description, int severity) {
        this.name = name;
        this.description = description;
        this.severity = severity;
    }
    
    /**
     * Execute the analyzer attack
     */
    public abstract void execute();
    
    /**
     * Get attack details
     */
    public String getDetails() {
        return String.format("Analyzer Attack: %s\nDescription: %s\nSeverity: %d/10", 
                            name, description, severity);
    }
    
    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSeverity() { return severity; }
}