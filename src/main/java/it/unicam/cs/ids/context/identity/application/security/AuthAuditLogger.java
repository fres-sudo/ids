package it.unicam.cs.ids.context.identity.application.security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Audit logger for authentication and authorization events.
 * Provides secure logging of security-related events without exposing sensitive information.
 */
@Component
@Slf4j
public class AuthAuditLogger {
    
    private static final Logger securityLogger = LoggerFactory.getLogger("SECURITY");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Logs an authentication attempt.
     *
     * @param email the email used in the attempt (will be masked for security)
     * @param success whether the authentication was successful
     * @param ipAddress the IP address of the client (optional)
     */
    public void logAuthenticationAttempt(String email, boolean success, String ipAddress) {
        String maskedEmail = maskEmail(email);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        try {
            // Add contextual information to MDC
            MDC.put("event_type", "AUTH_ATTEMPT");
            MDC.put("timestamp", timestamp);
            MDC.put("email", maskedEmail);
            MDC.put("success", String.valueOf(success));
            if (ipAddress != null) {
                MDC.put("ip_address", ipAddress);
            }
            
            if (success) {
                securityLogger.info("Authentication successful for email: {} from IP: {}", maskedEmail, ipAddress);
            } else {
                securityLogger.warn("Authentication failed for email: {} from IP: {}", maskedEmail, ipAddress);
            }
            
        } finally {
            MDC.clear();
        }
    }
    
    /**
     * Logs a registration event.
     *
     * @param email the email of the registered user (will be masked)
     * @param userType the type of user registered (USER, COMPANY, ADMIN, etc.)
     * @param success whether the registration was successful
     */
    public void logRegistrationEvent(String email, String userType, boolean success) {
        String maskedEmail = maskEmail(email);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        try {
            MDC.put("event_type", "REGISTRATION");
            MDC.put("timestamp", timestamp);
            MDC.put("email", maskedEmail);
            MDC.put("user_type", userType);
            MDC.put("success", String.valueOf(success));
            
            if (success) {
                securityLogger.info("Registration successful for {} with email: {}", userType, maskedEmail);
            } else {
                securityLogger.warn("Registration failed for {} with email: {}", userType, maskedEmail);
            }
            
        } finally {
            MDC.clear();
        }
    }
    
    /**
     * Logs an admin secret validation attempt.
     *
     * @param email the email attempting admin registration (will be masked)
     * @param success whether the secret validation was successful
     * @param ipAddress the IP address of the client (optional)
     */
    public void logAdminSecretAttempt(String email, boolean success, String ipAddress) {
        String maskedEmail = maskEmail(email);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        try {
            MDC.put("event_type", "ADMIN_SECRET_ATTEMPT");
            MDC.put("timestamp", timestamp);
            MDC.put("email", maskedEmail);
            MDC.put("success", String.valueOf(success));
            if (ipAddress != null) {
                MDC.put("ip_address", ipAddress);
            }
            
            if (success) {
                securityLogger.info("Admin secret validation successful for email: {} from IP: {}", maskedEmail, ipAddress);
            } else {
                securityLogger.error("Admin secret validation FAILED for email: {} from IP: {} - POTENTIAL SECURITY THREAT", maskedEmail, ipAddress);
            }
            
        } finally {
            MDC.clear();
        }
    }
    
    /**
     * Logs authorization events.
     *
     * @param email the email of the user (will be masked)
     * @param resource the resource being accessed
     * @param action the action being performed
     * @param granted whether access was granted
     */
    public void logAuthorizationEvent(String email, String resource, String action, boolean granted) {
        String maskedEmail = maskEmail(email);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        try {
            MDC.put("event_type", "AUTHORIZATION");
            MDC.put("timestamp", timestamp);
            MDC.put("email", maskedEmail);
            MDC.put("resource", resource);
            MDC.put("action", action);
            MDC.put("granted", String.valueOf(granted));
            
            if (granted) {
                securityLogger.debug("Access granted to {} for {} action on {}", maskedEmail, action, resource);
            } else {
                securityLogger.warn("Access denied to {} for {} action on {}", maskedEmail, action, resource);
            }
            
        } finally {
            MDC.clear();
        }
    }
    
    /**
     * Logs suspicious activity.
     *
     * @param email the email involved (will be masked)
     * @param activity description of the suspicious activity
     * @param ipAddress the IP address (optional)
     */
    public void logSuspiciousActivity(String email, String activity, String ipAddress) {
        String maskedEmail = maskEmail(email);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        try {
            MDC.put("event_type", "SUSPICIOUS_ACTIVITY");
            MDC.put("timestamp", timestamp);
            MDC.put("email", maskedEmail);
            MDC.put("activity", activity);
            if (ipAddress != null) {
                MDC.put("ip_address", ipAddress);
            }
            
            securityLogger.error("SUSPICIOUS ACTIVITY: {} for email: {} from IP: {}", activity, maskedEmail, ipAddress);
            
        } finally {
            MDC.clear();
        }
    }
    
    /**
     * Masks an email address for logging purposes.
     * Shows first 2 characters and domain, masks the rest.
     * Example: test@example.com -> te***@example.com
     *
     * @param email the email to mask
     * @return the masked email
     */
    private String maskEmail(String email) {
        if (email == null || email.length() < 5 || !email.contains("@")) {
            return "***@***.***";
        }
        
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        
        if (username.length() <= 2) {
            return "***" + domain;
        }
        
        String maskedUsername = username.substring(0, 2) + "***";
        return maskedUsername + domain;
    }
}