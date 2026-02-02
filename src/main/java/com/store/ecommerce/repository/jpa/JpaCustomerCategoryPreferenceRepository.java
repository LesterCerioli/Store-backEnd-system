package com.store.ecommerce.repository.jpa;

import com.store.ecommerce.domain.entity.CustomerCategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for CustomerCategoryPreference.
 */
@Repository
interface JpaCustomerCategoryPreferenceRepository extends JpaRepository<CustomerCategoryPreference, Long> {

    List<CustomerCategoryPreference> findByCustomerId(Long customerId);

    Optional<CustomerCategoryPreference> findByCustomerIdAndCategoryId(Long customerId, Long categoryId);

    boolean existsByCustomerIdAndCategoryId(Long customerId, Long categoryId);

    @Modifying
    @Query("DELETE FROM CustomerCategoryPreference c WHERE c.customer.id = :customerId AND c.category.id = :categoryId")
    void deleteByCustomerIdAndCategoryId(@Param("customerId") Long customerId, @Param("categoryId") Long categoryId);
}
