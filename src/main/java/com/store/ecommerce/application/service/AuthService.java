package com.store.ecommerce.application.service;

import java.util.Optional;

/**
 * Service contract for authentication and JWT token operations.
 * Single Responsibility: auth-related use cases only.
 * Dependency Inversion: application depends on this abstraction.
 */
public interface AuthService {

    /**
     * Generates a JWT token for valid client credentials and stores it in the database.
     *
     * @param clientId   the client identifier
     * @param clientSecret the client secret
     * @return token response containing token, clientId, and expiresAt
     * @throws IllegalArgumentException if client_id or secret is invalid
     */
    TokenResponse generateToken(String clientId, String clientSecret);

    /**
     * Validates a JWT token against the database (exists and not expired) and cryptographic signature.
     *
     * @param tokenString the JWT token string
     * @return true if token is valid, false otherwise
     */
    boolean validateToken(String tokenString);

    /**
     * Gets a valid (non-expired) token for the given client_id from the database.
     *
     * @param clientId the client identifier
     * @return the latest valid token if exists, empty otherwise
     */
    Optional<String> getValidToken(String clientId);

    /**
     * Removes expired tokens from the database.
     *
     * @return the number of deleted tokens
     */
    int cleanupExpiredTokens();

    /**
     * Response DTO for generateToken.
     */
    record TokenResponse(String token, String clientId, String expiresAt) {}
}
