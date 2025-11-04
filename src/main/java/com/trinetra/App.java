package com.trinetra;

import com.trinetra.core.TrinetraEngine;
import com.trinetra.utils.Logger;

/**
 * Trinetra - An advanced Java-based cybersecurity tool built to detect vulnerabilities,
 * analyze web threats, and strengthen digital defenses.
 * 
 * Inspired by Lord Shiva's Third Eye - the Trinetra, representing divine perception 
 * and unmatched awareness in the cyber realm.
 */
public class App 
{
    public static void main( String[] args )
    {
        Logger.info("Starting Trinetra Cybersecurity Tool...");
        Logger.info("Version 1.0-SNAPSHOT");
        Logger.info("=====================================");
        
        try {
            TrinetraEngine engine = new TrinetraEngine();
            engine.initialize();
            
            if (args.length == 0) {
                engine.showHelp();
            } else {
                engine.processCommand(args);
            }
            
            Logger.info("Trinetra execution completed.");
        } catch (Exception e) {
            Logger.error("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}