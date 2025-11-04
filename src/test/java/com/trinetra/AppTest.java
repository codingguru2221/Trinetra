package com.trinetra;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Trinetra application
 */
public class AppTest 
{
    /**
     * Tests that the application can be instantiated
     */
    @Test
    public void testAppCreation()
    {
        App app = new App();
        assertNotNull(app);
    }
    
    /**
     * Tests the main method with no arguments
     */
    @Test
    public void testMainWithNoArgs()
    {
        // This test just verifies that the main method runs without throwing exceptions
        // In a real application, we would use a testing framework to capture System.out
        String[] args = {};
        try {
            App.main(args);
            // If we get here, the main method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("Main method should not throw exceptions with no args: " + e.getMessage());
        }
    }
    
    /**
     * Tests the main method with help argument
     */
    @Test
    public void testMainWithHelpArg()
    {
        // This test just verifies that the main method runs without throwing exceptions
        String[] args = {"help"};
        try {
            App.main(args);
            // If we get here, the main method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("Main method should not throw exceptions with help arg: " + e.getMessage());
        }
    }
}