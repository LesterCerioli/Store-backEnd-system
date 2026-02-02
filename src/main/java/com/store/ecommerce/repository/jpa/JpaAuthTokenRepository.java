package com.store.ecommerce.repository.jpa;

import com.store.ecommerce.domain.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;


@Repository
interface JpaAuthTokenRepository extends JpaRepository<AuthToken, String> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AuthToken a WHERE a.jwtToken = :token AND a.expiresAt > :now")
    boolean existsByJwtTokenAndExpiresAtAfter(@Param("token") String token, @Param("now") Instant now);

    Optional<AuthToken> findTop1ByClientIdAndExpiresAtAfterOrderByCreatedAtDesc(String clientId, Instant now);

    @Modifying
    @Query("DELETE FROM AuthToken a WHERE a.expiresAt <= :now")
    int deleteByExpiresAtBefore(@Param("now") Instant now);
}
