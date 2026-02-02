package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.AuthToken;

import java.util.Optional;

/**
 * Repository contract for AuthToken entity.
 * Open/Closed: extend behavior via implementations without modifying this contract.
 */
public interface AuthTokenRepository {

    AuthToken save(AuthToken authToken);

    boolean existsByJwtTokenAndNotExpired(String jwtToken);

    Optional<String> findLatestValidTokenByClientId(String clientId);

    int deleteExpiredTokens();
}
