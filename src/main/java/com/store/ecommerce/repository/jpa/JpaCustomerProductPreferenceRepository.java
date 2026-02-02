package com.store.ecommerce.repository.jpa;

import com.store.ecommerce.domain.entity.CustomerProductPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for CustomerProductPreference.
 */
@Repository
interface JpaCustomerProductPreferenceRepository extends JpaRepository<CustomerProductPreference, Long> {

    List<CustomerProductPreference> findByCustomerId(Long customerId);

    Optional<CustomerProductPreference> findByCustomerIdAndProductId(Long customerId, Long productId);

    boolean existsByCustomerIdAndProductId(Long customerId, Long productId);

    @Modifying
    @Query("DELETE FROM CustomerProductPreference c WHERE c.customer.id = :customerId AND c.product.id = :productId")
    void deleteByCustomerIdAndProductId(@Param("customerId") Long customerId, @Param("productId") Long productId);
}
