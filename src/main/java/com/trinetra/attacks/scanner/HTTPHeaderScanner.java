package com.trinetra.attacks.scanner;

import com.trinetra.utils.Logger;
import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HTTPHeaderScanner {
    private static final Map<String, String> SECURITY_HEADERS = new HashMap<String, String>() {{
        put("X-Frame-Options", "Missing X-Frame-Options header - vulnerable to clickjacking");
        put("X-Content-Type-Options", "Missing X-Content-Type-Options header - vulnerable to MIME sniffing");
        put("X-XSS-Protection", "Missing X-XSS-Protection header");
        put("Content-Security-Policy", "Missing Content-Security-Policy header");
        put("Strict-Transport-Security", "Missing HSTS header - vulnerable to SSL stripping");
        put("Referrer-Policy", "Missing Referrer-Policy header");
        put("Permissions-Policy", "Missing Permissions-Policy header");
        put("Access-Control-Allow-Origin", "Check CORS policy configuration");
    }};

    private static final List<String> DANGEROUS_HEADERS = Arrays.asList(
        "X-Powered-By",
        "Server",
        "X-AspNet-Version",
        "X-AspNetMvc-Version"
    );

    private final HttpClient httpClient;

    public HTTPHeaderScanner() {
        this.httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    }

    public Map<String, List<String>> scanHeaders(String targetUrl) {
        Map<String, List<String>> vulnerabilities = new HashMap<>();
        List<String> findings = new ArrayList<>();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Map<String, List<String>> headers = response.headers().map();

            // Check for missing security headers
            for (Map.Entry<String, String> header : SECURITY_HEADERS.entrySet()) {
                if (!headers.containsKey(header.getKey().toLowerCase())) {
                    findings.add(header.getValue());
                    Logger.warn("Security header missing: " + header.getKey());
                }
            }

            // Check for dangerous headers
            for (String header : DANGEROUS_HEADERS) {
                if (headers.containsKey(header.toLowerCase())) {
                    String value = headers.get(header.toLowerCase()).get(0);
                    findings.add("Information disclosure through " + header + " header: " + value);
                    Logger.warn("Information disclosure through header: " + header);
                }
            }

            // Analyze specific header values
            analyzeHeaderValues(headers, findings);

            if (!findings.isEmpty()) {
                vulnerabilities.put("HTTP Security Headers", findings);
            }

        } catch (Exception e) {
            Logger.error("Error during header scan: " + e.getMessage());
        }

        return vulnerabilities;
    }

    private void analyzeHeaderValues(Map<String, List<String>> headers, List<String> findings) {
        // Check CORS configuration
        if (headers.containsKey("access-control-allow-origin")) {
            String origin = headers.get("access-control-allow-origin").get(0);
            if ("*".equals(origin)) {
                findings.add("Overly permissive CORS policy (Access-Control-Allow-Origin: *)");
                Logger.warn("Overly permissive CORS policy detected");
            }
        }

        // Check CSP strength
        if (headers.containsKey("content-security-policy")) {
            String csp = headers.get("content-security-policy").get(0);
            if (csp.contains("unsafe-inline") || csp.contains("unsafe-eval")) {
                findings.add("Weak Content-Security-Policy: Contains unsafe directives");
                Logger.warn("Weak CSP configuration detected");
            }
        }

        // Check HSTS configuration
        if (headers.containsKey("strict-transport-security")) {
            String hsts = headers.get("strict-transport-security").get(0);
            if (!hsts.contains("includeSubDomains") || !hsts.contains("preload")) {
                findings.add("Incomplete HSTS configuration: Missing includeSubDomains or preload");
                Logger.warn("Incomplete HSTS configuration detected");
            }
        }

        // Check Cookie security
        if (headers.containsKey("set-cookie")) {
            for (String cookie : headers.get("set-cookie")) {
                if (!cookie.contains("Secure") || !cookie.contains("HttpOnly")) {
                    findings.add("Insecure cookie configuration: Missing Secure or HttpOnly flags");
                    Logger.warn("Insecure cookie configuration detected");
                    break;
                }
            }
        }
    }
}