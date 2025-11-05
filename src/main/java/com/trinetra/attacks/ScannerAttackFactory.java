package com.trinetra.attacks;

import com.trinetra.attacks.scanner.PortScanner;
import com.trinetra.attacks.scanner.DirectoryEnumerator;
import com.trinetra.attacks.scanner.SSLTLSScanner;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating Scanner Attack instances
 */
public class ScannerAttackFactory {
    
    private static final Map<String, String> attackDescriptions = new HashMap<>();
    
    static {
        attackDescriptions.put("portscan", "Scans open ports on target system");
        attackDescriptions.put("directory", "Finds hidden directories and files");
        attackDescriptions.put("ssl", "Analyzes SSL/TLS configuration for vulnerabilities");
    }
    
    /**
     * Create a scanner attack by type
     * @param attackType Type of scanner attack
     * @param target Target for the attack
     * @return ScannerAttack instance
     */
    public static ScannerAttack createScannerAttack(String attackType, String target) {
        switch (attackType.toLowerCase()) {
            case "portscan":
                return new PortScanner(target);
            case "directory":
                return new DirectoryEnumerator(target);
            case "ssl":
                return new SSLTLSScanner(target);
            default:
                throw new IllegalArgumentException("Unknown scanner attack type: " + attackType);
        }
    }
    
    /**
     * Get available scanner attack types
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