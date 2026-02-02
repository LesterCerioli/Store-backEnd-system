package com.store.ecommerce.repository.impl;

import com.store.ecommerce.domain.entity.AuthToken;
import com.store.ecommerce.domain.repository.AuthTokenRepository;
import com.store.ecommerce.repository.jpa.JpaAuthTokenRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;


@Repository
public class AuthTokenRepositoryImpl implements AuthTokenRepository {

    private final JpaAuthTokenRepository jpaRepository;

    public AuthTokenRepositoryImpl(JpaAuthTokenRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AuthToken save(AuthToken authToken) {
        return jpaRepository.save(authToken);
    }

    @Override
    public boolean existsByJwtTokenAndNotExpired(String jwtToken) {
        return jpaRepository.existsByJwtTokenAndExpiresAtAfter(jwtToken, Instant.now());
    }

    @Override
    public Optional<String> findLatestValidTokenByClientId(String clientId) {
        return jpaRepository.findTop1ByClientIdAndExpiresAtAfterOrderByCreatedAtDesc(clientId, Instant.now())
                .map(AuthToken::getJwtToken);
    }

    @Override
    public int deleteExpiredTokens() {
        return jpaRepository.deleteByExpiresAtBefore(Instant.now());
    }
}
