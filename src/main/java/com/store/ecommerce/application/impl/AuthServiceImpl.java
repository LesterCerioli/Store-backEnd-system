package com.store.ecommerce.application.impl;

import com.store.ecommerce.application.config.AuthProperties;
import com.store.ecommerce.application.service.AuthService;
import com.store.ecommerce.domain.entity.AuthToken;
import com.store.ecommerce.domain.repository.AuthTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of AuthService. Similar to Python AuthTokenService.
 * Generates and validates JWT tokens, stores them in the database.
 * Single Responsibility: auth business logic only.
 * Dependency Injection: receives AuthTokenRepository and AuthProperties via constructor.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final int TOKEN_EXPIRATION_MINUTES = 2;

    private final AuthTokenRepository authTokenRepository;
    private final AuthProperties authProperties;
    private final SecretKey jwtSecretKey;

    public AuthServiceImpl(AuthTokenRepository authTokenRepository, AuthProperties authProperties) {
        this.authTokenRepository = authTokenRepository;
        this.authProperties = authProperties;
        String secret = authProperties.getJwt().getSecret();
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT_SECRET must have 32+ characters");
        }
        if (authProperties.getClientCredentialsMap().isEmpty()) {
            throw new IllegalArgumentException("CLIENT_ID_1 and SECRET_1 must be set in environment or config");
        }
        this.jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    @Transactional
    public TokenResponse generateToken(String clientId, String clientSecret) {
        logger.info("Starting token generation for client_id={}", clientId);

        Map<String, String> credentials = authProperties.getClientCredentialsMap();
        String storedSecret = credentials.get(clientId);
        if (storedSecret == null) {
            logger.warn("client_id not found: {}", clientId);
            throw new IllegalArgumentException("invalid client_id or secret");
        }
        if (!storedSecret.equals(clientSecret)) {
            logger.warn("Invalid secret for client_id={}", clientId);
            throw new IllegalArgumentException("invalid client_id or secret");
        }

        Instant expiration = Instant.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES);
        logger.debug("Expiration set for: {}", expiration);

        String tokenString;
        try {
            tokenString = Jwts.builder()
                    .subject(clientId)
                    .claim("client_id", clientId)
                    .expiration(java.util.Date.from(expiration))
                    .signWith(jwtSecretKey)
                    .compact();
            logger.info("JWT token generated successfully for client_id={}", clientId);
        } catch (Exception e) {
            logger.error("Failed to sign JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("error generating token: " + e.getMessage());
        }

        try {
            AuthToken authToken = new AuthToken();
            authToken.setClientId(clientId);
            authToken.setJwtToken(tokenString);
            authToken.setExpiresAt(expiration);
            authTokenRepository.save(authToken);
            logger.info("Token saved to database successfully for client_id={}", clientId);
        } catch (Exception e) {
            logger.error("Failed to save token to database: {}", e.getMessage());
            throw new IllegalArgumentException("error to save on database: " + e.getMessage());
        }

        return new TokenResponse(
                tokenString,
                clientId,
                expiration.toString()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String tokenString) {
        try {
            if (!authTokenRepository.existsByJwtTokenAndNotExpired(tokenString)) {
                return false;
            }
            Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(tokenString);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token expired");
            return false;
        } catch (Exception e) {
            logger.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getValidToken(String clientId) {
        try {
            return authTokenRepository.findLatestValidTokenByClientId(clientId);
        } catch (Exception e) {
            logger.error("Error fetching valid token: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public int cleanupExpiredTokens() {
        try {
            int deletedCount = authTokenRepository.deleteExpiredTokens();
            if (deletedCount > 0) {
                logger.info("Cleanup of expired tokens: {} tokens removed", deletedCount);
            }
            return deletedCount;
        } catch (Exception e) {
            logger.error("Error cleaning expired tokens: {}", e.getMessage());
            return 0;
        }
    }
}
