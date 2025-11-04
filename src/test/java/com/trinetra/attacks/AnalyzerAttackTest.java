package com.trinetra.attacks;

import com.trinetra.attacks.analyzer.SQLInjectionAnalyzer;
import com.trinetra.attacks.analyzer.XSSAnalyzer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Analyzer Attack classes
 */
public class AnalyzerAttackTest extends TestCase {
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AnalyzerAttackTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AnalyzerAttackTest.class);
    }

    /**
     * Test SQLInjectionAnalyzer creation and basic functionality
     */
    public void testSQLInjectionAnalyzerCreation() {
        SQLInjectionAnalyzer sqliAnalyzer = new SQLInjectionAnalyzer("http://example.com/login");
        assertNotNull("SQLInjectionAnalyzer should be created", sqliAnalyzer);
        assertEquals("Name should be 'SQL Injection Analyzer'", "SQL Injection Analyzer", sqliAnalyzer.getName());
        assertEquals("Description should match", "Tests for SQL injection vulnerabilities", sqliAnalyzer.getDescription());
        assertEquals("Severity should be 9", 9, sqliAnalyzer.getSeverity());
    }
    
    /**
     * Test XSSAnalyzer creation and basic functionality
     */
    public void testXSSAnalyzerCreation() {
        XSSAnalyzer xssAnalyzer = new XSSAnalyzer("http://example.com/search");
        assertNotNull("XSSAnalyzer should be created", xssAnalyzer);
        assertEquals("Name should be 'XSS Analyzer'", "XSS Analyzer", xssAnalyzer.getName());
        assertEquals("Description should match", "Tests for Cross-Site Scripting vulnerabilities", xssAnalyzer.getDescription());
        assertEquals("Severity should be 7", 7, xssAnalyzer.getSeverity());
    }
    
    /**
     * Test AnalyzerAttackFactory
     */
    public void testAnalyzerAttackFactory() {
        // Test available attack types
        String[] attackTypes = AnalyzerAttackFactory.getAvailableAttackTypes();
        assertTrue("Should have at least 2 attack types", attackTypes.length >= 2);
        
        // Test creating attacks
        AnalyzerAttack sqli = AnalyzerAttackFactory.createAnalyzerAttack("sqli", "http://example.com/login");
        assertNotNull("Should create SQLInjectionAnalyzer", sqli);
        assertTrue("Should be SQLInjectionAnalyzer instance", sqli instanceof SQLInjectionAnalyzer);
        
        AnalyzerAttack xss = AnalyzerAttackFactory.createAnalyzerAttack("xss", "http://example.com/search");
        assertNotNull("Should create XSSAnalyzer", xss);
        assertTrue("Should be XSSAnalyzer instance", xss instanceof XSSAnalyzer);
        
        // Test unknown attack type
        try {
            AnalyzerAttack unknown = AnalyzerAttackFactory.createAnalyzerAttack("unknown", "http://example.com");
            fail("Should throw IllegalArgumentException for unknown attack type");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}