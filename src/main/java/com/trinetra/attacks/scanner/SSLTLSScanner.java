package com.trinetra.attacks.scanner;

import com.trinetra.attacks.ScannerAttack;
import com.trinetra.utils.Logger;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.util.*;
import java.net.Socket;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * SSL/TLS scanning attack implementation
 */
public class SSLTLSScanner extends ScannerAttack {
    private static final List<String> WEAK_CIPHERS = Arrays.asList(
        "TLS_RSA_WITH_RC4",
        "SSL_RSA_WITH_RC4",
        "TLS_RSA_WITH_3DES",
        "SSL_RSA_WITH_3DES",
        "TLS_RSA_WITH_NULL",
        "SSL_RSA_WITH_NULL"
    );

    private static final List<String> WEAK_PROTOCOLS = Arrays.asList(
        "SSLv2",
        "SSLv3",
        "TLSv1.0",
        "TLSv1.1"
    );
    
    private String target;
    private int port;

    public SSLTLSScanner(String target) {
        super("SSL/TLS Scanner", "Analyzes SSL/TLS configuration for vulnerabilities", 8);
        this.target = target;
        // Extract port from target URL or default to 443
        this.port = extractPort(target);
    }
    
    private int extractPort(String target) {
        try {
            URL url = new URL(target);
            int port = url.getPort();
            if (port == -1) {
                // Default to HTTPS port if not specified
                return url.getProtocol().equals("https") ? 443 : 80;
            }
            return port;
        } catch (Exception e) {
            // Default to HTTPS port
            return 443;
        }
    }

    @Override
    public void execute() {
        Logger.info("Executing SSL/TLS Scanner on " + target);
        System.out.println("Scanning SSL/TLS configuration on " + target + ":" + port + "...");
        
        Map<String, List<String>> vulnerabilities = scanSSLTLS(getHost(target), port);
        
        if (vulnerabilities.isEmpty()) {
            System.out.println("No SSL/TLS vulnerabilities found.");
        } else {
            System.out.println("SSL/TLS Vulnerabilities Found:");
            for (Map.Entry<String, List<String>> entry : vulnerabilities.entrySet()) {
                for (String finding : entry.getValue()) {
                    System.out.println("  - " + finding);
                }
            }
        }
        
        System.out.println("SSL/TLS scan completed.");
    }
    
    private String getHost(String target) {
        try {
            URL url = new URL(target);
            return url.getHost();
        } catch (Exception e) {
            return target;
        }
    }

    public Map<String, List<String>> scanSSLTLS(String host, int port) {
        Map<String, List<String>> vulnerabilities = new HashMap<>();
        List<String> findings = new ArrayList<>();

        try {
            // Test SSL/TLS protocols
            checkProtocols(host, port, findings);

            // Test cipher suites
            checkCiphers(host, port, findings);

            // Check certificate
            checkCertificate(host, port, findings);

            if (!findings.isEmpty()) {
                vulnerabilities.put("SSL/TLS Vulnerabilities", findings);
            }

        } catch (Exception e) {
            Logger.error("Error during SSL/TLS scan: " + e.getMessage());
            System.out.println("Error during SSL/TLS scan: " + e.getMessage());
        }

        return vulnerabilities;
    }

    private void checkProtocols(String host, int port, List<String> findings) {
        for (String protocol : WEAK_PROTOCOLS) {
            try {
                SSLContext context = SSLContext.getInstance(protocol);
                context.init(null, new TrustManager[] { new TrustAllManager() }, null);
                SSLSocketFactory factory = context.getSocketFactory();

                try (Socket socket = factory.createSocket(host, port)) {
                    findings.add("Vulnerable Protocol Supported: " + protocol);
                    Logger.warn("Server supports weak protocol: " + protocol);
                } catch (IOException e) {
                    // Protocol not supported - this is good
                }
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                // Protocol not available in Java
            }
        }
    }

    private void checkCiphers(String host, int port, List<String> findings) {
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
                for (String cipher : socket.getSupportedCipherSuites()) {
                    for (String weakCipher : WEAK_CIPHERS) {
                        if (cipher.contains(weakCipher)) {
                            findings.add("Weak Cipher Supported: " + cipher);
                            Logger.warn("Server supports weak cipher: " + cipher);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.error("Error checking ciphers: " + e.getMessage());
        }
    }

    private void checkCertificate(String host, int port, List<String> findings) {
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
                socket.startHandshake();
                SSLSession session = socket.getSession();
                X509Certificate[] certs = (X509Certificate[]) session.getPeerCertificates();

                if (certs.length > 0) {
                    X509Certificate cert = certs[0];
                    
                    // Check expiration
                    try {
                        cert.checkValidity();
                    } catch (CertificateException e) {
                        findings.add("Certificate is expired or not yet valid");
                        Logger.warn("Invalid certificate date for " + host);
                    }

                    // Check key size
                    String pubKeyAlg = cert.getPublicKey().getAlgorithm();
                    // For RSA keys, we can check the actual key size
                    if ("RSA".equals(pubKeyAlg)) {
                        int keySize = cert.getPublicKey().getEncoded().length * 8;
                        if (keySize < 2048) {
                            findings.add("Weak certificate key size: " + keySize + " bits");
                            Logger.warn("Weak certificate key size for " + host + ": " + keySize + " bits");
                        }
                    }

                    // Check signature algorithm
                    String sigAlg = cert.getSigAlgName().toUpperCase();
                    if (sigAlg.contains("MD5") || sigAlg.contains("SHA1")) {
                        findings.add("Weak signature algorithm: " + sigAlg);
                        Logger.warn("Weak signature algorithm for " + host + ": " + sigAlg);
                    }
                }
            }
        } catch (Exception e) {
            Logger.error("Error checking certificate: " + e.getMessage());
        }
    }

    private static class TrustAllManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
    }
}