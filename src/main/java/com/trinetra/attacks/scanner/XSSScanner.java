package com.trinetra.attacks.scanner;

import com.trinetra.utils.Logger;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class XSSScanner {
    private static final List<String> XSS_PAYLOADS = Arrays.asList(
        "<script>alert('xss')</script>",
        "<img src=x onerror=alert('xss')>",
        "<svg onload=alert('xss')>",
        "javascript:alert('xss')",
        "\"><script>alert('xss')</script>",
        "'><script>alert('xss')</script>",
        "<body onload=alert('xss')>",
        "<img src=\"javascript:alert('xss')\">",
        "<script>eval(String.fromCharCode(97,108,101,114,116,40,39,120,115,115,39,41))</script>"
    );

    public Map<String, List<String>> scanForXSS(String targetUrl) {
        Map<String, List<String>> vulnerabilities = new HashMap<>();
        List<String> foundVulnerabilities = new ArrayList<>();

        for (String payload : XSS_PAYLOADS) {
            try {
                // Test GET parameters
                String testUrl = targetUrl + "?input=" + java.net.URLEncoder.encode(payload, "UTF-8");
                HttpURLConnection conn = (HttpURLConnection) new URL(testUrl).openConnection();
                conn.setRequestMethod("GET");

                // Check response
                String response = readResponse(conn);
                if (response.contains(payload)) {
                    foundVulnerabilities.add("Potential XSS (GET): " + payload);
                    Logger.warn("Found potential XSS vulnerability with payload: " + payload);
                }

                // Test POST parameters
                conn = (HttpURLConnection) new URL(targetUrl).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                    writer.write("input=" + java.net.URLEncoder.encode(payload, "UTF-8"));
                }

                response = readResponse(conn);
                if (response.contains(payload)) {
                    foundVulnerabilities.add("Potential XSS (POST): " + payload);
                    Logger.warn("Found potential XSS vulnerability with POST payload: " + payload);
                }

            } catch (Exception e) {
                Logger.error("Error during XSS scan: " + e.getMessage());
            }
        }

        if (!foundVulnerabilities.isEmpty()) {
            vulnerabilities.put("Cross-Site Scripting (XSS)", foundVulnerabilities);
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