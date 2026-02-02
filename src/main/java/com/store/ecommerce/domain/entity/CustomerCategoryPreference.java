package com.store.ecommerce.domain.entity;

import jakarta.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "customer_category_preferences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "category_id"}))
public class CustomerCategoryPreference extends BaseEntity {

    @Column(name = "preference_score")
    private Integer preferenceScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Integer getPreferenceScore() {
        return preferenceScore;
    }

    public void setPreferenceScore(Integer preferenceScore) {
        this.preferenceScore = preferenceScore;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCategoryPreference that = (CustomerCategoryPreference) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
