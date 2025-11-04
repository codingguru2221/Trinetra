package com.trinetra.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the TrinetraEngine class
 */
public class TrinetraEngineTest 
{
    /**
     * Tests that the TrinetraEngine can be instantiated
     */
    @Test
    public void testEngineCreation()
    {
        TrinetraEngine engine = new TrinetraEngine();
        assertNotNull(engine);
    }
    
    /**
     * Tests the initialization of the TrinetraEngine
     */
    @Test
    public void testEngineInitialization()
    {
        TrinetraEngine engine = new TrinetraEngine();
        try {
            engine.initialize();
            // If we get here, initialization succeeded
            assertTrue(true);
        } catch (Exception e) {
            fail("Engine initialization should not throw exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests the showHelp method
     */
    @Test
    public void testShowHelp()
    {
        TrinetraEngine engine = new TrinetraEngine();
        try {
            engine.initialize();
            engine.showHelp();
            // If we get here, showHelp succeeded
            assertTrue(true);
        } catch (Exception e) {
            fail("showHelp method should not throw exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests processing of unknown command
     */
    @Test
    public void testProcessUnknownCommand()
    {
        TrinetraEngine engine = new TrinetraEngine();
        try {
            engine.initialize();
            String[] args = {"unknown"};
            engine.processCommand(args);
            // If we get here, processing unknown command succeeded
            assertTrue(true);
        } catch (Exception e) {
            fail("processCommand should handle unknown commands without throwing exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Tests processing of help command
     */
    @Test
    public void testProcessHelpCommand()
    {
        TrinetraEngine engine = new TrinetraEngine();
        try {
            engine.initialize();
            String[] args = {"help"};
            engine.processCommand(args);
            // If we get here, processing help command succeeded
            assertTrue(true);
        } catch (Exception e) {
            fail("processCommand should handle help command without throwing exceptions: " + e.getMessage());
        }
    }
}