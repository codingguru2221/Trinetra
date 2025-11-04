package com.trinetra.analyzer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the ThreatAnalyzer class
 */
public class ThreatAnalyzerTest 
{
    /**
     * Tests that the ThreatAnalyzer can be instantiated
     */
    @Test
    public void testAnalyzerCreation()
    {
        ThreatAnalyzer analyzer = new ThreatAnalyzer();
        assertNotNull(analyzer);
    }
    
    /**
     * Tests the analyzeThreats method with no arguments
     */
    @Test
    public void testAnalyzeThreatsWithNoArgs()
    {
        ThreatAnalyzer analyzer = new ThreatAnalyzer();
        try {
            String[] args = {};
            analyzer.analyzeThreats(args);
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("analyzeThreats should handle empty args without throwing exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests the analyzeThreats method with file argument
     */
    @Test
    public void testAnalyzeThreatsWithFile()
    {
        ThreatAnalyzer analyzer = new ThreatAnalyzer();
        try {
            String[] args = {"--file", "test.log"};
            analyzer.analyzeThreats(args);
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("analyzeThreats should handle file args without throwing exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests the analyzeThreats method with detailed argument
     */
    @Test
    public void testAnalyzeThreatsWithDetailed()
    {
        ThreatAnalyzer analyzer = new ThreatAnalyzer();
        try {
            String[] args = {"--file", "test.log", "--detailed"};
            analyzer.analyzeThreats(args);
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("analyzeThreats should handle detailed args without throwing exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests the analyzeThreats method with network analysis type
     */
    @Test
    public void testAnalyzeThreatsWithNetworkType()
    {
        ThreatAnalyzer analyzer = new ThreatAnalyzer();
        try {
            String[] args = {"--file", "test.log", "--type", "network"};
            analyzer.analyzeThreats(args);
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("analyzeThreats should handle network type args without throwing exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests the analyzeThreats method with malware analysis type
     */
    @Test
    public void testAnalyzeThreatsWithMalwareType()
    {
        ThreatAnalyzer analyzer = new ThreatAnalyzer();
        try {
            String[] args = {"--file", "test.log", "--type", "malware"};
            analyzer.analyzeThreats(args);
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("analyzeThreats should handle malware type args without throwing exceptions: " + e.getMessage());
        }
    }
}