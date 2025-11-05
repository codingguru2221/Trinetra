package com.trinetra.attacks.scanner;

import com.trinetra.attacks.ScannerAttackFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class SSLTLSScannerTest {
    
    @Test
    public void testSSLScannerCreation() {
        // Test that we can create an SSL scanner instance
        SSLTLSScanner scanner = new SSLTLSScanner("https://www.google.com");
        assertNotNull(scanner);
        assertEquals("SSL/TLS Scanner", scanner.getName());
        assertEquals(8, scanner.getSeverity());
    }
    
    @Test
    public void testSSLScannerFactory() {
        // Test that the factory can create an SSL scanner
        SSLTLSScanner scanner = (SSLTLSScanner) ScannerAttackFactory.createScannerAttack("ssl", "https://www.google.com");
        assertNotNull(scanner);
        assertEquals("SSL/TLS Scanner", scanner.getName());
    }
}