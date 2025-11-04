package com.trinetra.attacks;

import com.trinetra.attacks.analyzer.SQLInjectionAnalyzer;
import com.trinetra.attacks.analyzer.XSSAnalyzer;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating Analyzer Attack instances
 */
public class AnalyzerAttackFactory {
    
    private static final Map<String, String> attackDescriptions = new HashMap<>();
    
    static {
        attackDescriptions.put("sqli", "Tests for SQL injection vulnerabilities");
        attackDescriptions.put("xss", "Tests for Cross-Site Scripting vulnerabilities");
    }
    
    /**
     * Create an analyzer attack by type
     * @param attackType Type of analyzer attack
     * @param target Target for the attack
     * @return AnalyzerAttack instance
     */
    public static AnalyzerAttack createAnalyzerAttack(String attackType, String target) {
        switch (attackType.toLowerCase()) {
            case "sqli":
                return new SQLInjectionAnalyzer(target);
            case "xss":
                return new XSSAnalyzer(target);
            default:
                throw new IllegalArgumentException("Unknown analyzer attack type: " + attackType);
        }
    }
    
    /**
     * Get available analyzer attack types
     * @return Array of available attack types
     */
    public static String[] getAvailableAttackTypes() {
        return attackDescriptions.keySet().toArray(new String[0]);
    }
    
    /**
     * Get description for an attack type
     * @param attackType The attack type
     * @return Description of the attack
     */
    public static String getAttackDescription(String attackType) {
        return attackDescriptions.getOrDefault(attackType.toLowerCase(), "Unknown attack type");
    }
}