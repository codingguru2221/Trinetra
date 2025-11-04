package com.trinetra.attacks;

import com.trinetra.attacks.scanner.PortScanner;
import com.trinetra.attacks.scanner.DirectoryEnumerator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Scanner Attack classes
 */
public class ScannerAttackTest extends TestCase {
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ScannerAttackTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ScannerAttackTest.class);
    }

    /**
     * Test PortScanner creation and basic functionality
     */
    public void testPortScannerCreation() {
        PortScanner portScanner = new PortScanner("http://example.com");
        assertNotNull("PortScanner should be created", portScanner);
        assertEquals("Name should be 'Port Scanner'", "Port Scanner", portScanner.getName());
        assertEquals("Description should match", "Scans open ports on target system", portScanner.getDescription());
        assertEquals("Severity should be 6", 6, portScanner.getSeverity());
    }
    
    /**
     * Test DirectoryEnumerator creation and basic functionality
     */
    public void testDirectoryEnumeratorCreation() {
        DirectoryEnumerator dirEnum = new DirectoryEnumerator("http://example.com");
        assertNotNull("DirectoryEnumerator should be created", dirEnum);
        assertEquals("Name should be 'Directory Enumerator'", "Directory Enumerator", dirEnum.getName());
        assertEquals("Description should match", "Finds hidden directories and files", dirEnum.getDescription());
        assertEquals("Severity should be 5", 5, dirEnum.getSeverity());
    }
    
    /**
     * Test ScannerAttackFactory
     */
    public void testScannerAttackFactory() {
        // Test available attack types
        String[] attackTypes = ScannerAttackFactory.getAvailableAttackTypes();
        assertTrue("Should have at least 2 attack types", attackTypes.length >= 2);
        
        // Test creating attacks
        ScannerAttack portScan = ScannerAttackFactory.createScannerAttack("portscan", "http://example.com");
        assertNotNull("Should create PortScanner", portScan);
        assertTrue("Should be PortScanner instance", portScan instanceof PortScanner);
        
        ScannerAttack dirEnum = ScannerAttackFactory.createScannerAttack("directory", "http://example.com");
        assertNotNull("Should create DirectoryEnumerator", dirEnum);
        assertTrue("Should be DirectoryEnumerator instance", dirEnum instanceof DirectoryEnumerator);
        
        // Test unknown attack type
        try {
            ScannerAttack unknown = ScannerAttackFactory.createScannerAttack("unknown", "http://example.com");
            fail("Should throw IllegalArgumentException for unknown attack type");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}