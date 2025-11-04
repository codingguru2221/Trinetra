package com.trinetra.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Logger utility class
 */
public class LoggerTest 
{
    /**
     * Tests that the Logger can log info messages
     */
    @Test
    public void testInfoLogging()
    {
        try {
            Logger.info("Test info message");
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("Logger.info should not throw exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the Logger can log error messages
     */
    @Test
    public void testErrorLogging()
    {
        try {
            Logger.error("Test error message");
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("Logger.error should not throw exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the Logger can log debug messages
     */
    @Test
    public void testDebugLogging()
    {
        try {
            Logger.debug("Test debug message");
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("Logger.debug should not throw exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the Logger can log warn messages
     */
    @Test
    public void testWarnLogging()
    {
        try {
            Logger.warn("Test warn message");
            // If we get here, the method ran without throwing exceptions
            assertTrue(true);
        } catch (Exception e) {
            fail("Logger.warn should not throw exceptions: " + e.getMessage());
        }
    }
}