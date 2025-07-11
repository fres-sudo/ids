package it.unicam.cs.ids.context.catalog.infrastructure.persistence.specifications;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.ids.shared.infrastructure.persistence.AbstractSpecification;
import org.springframework.data.jpa.domain.Specification;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleFilter;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import jakarta.persistence.criteria.Predicate;

public final class BundleSpecification extends AbstractSpecification {
    public static Specification<Bundle> withFilter(BundleFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filter by Categories
            if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
                predicates.add(root.get("category").in(filter.getCategories()));
            }

            // 2. Filter by Available
            if (filter.isAvailable()) {
                predicates.add(criteriaBuilder.greaterThan(root.get("quantity"), 0));
            }

            // 3. Filter by Available for Shipping
            if (filter.isAvailableForShipping()) {
                predicates.add(criteriaBuilder.isTrue(root.get("availableForShipping")));
            }

            // 4. Filter by Minimum Discount Percentage
            if (filter.getMinDiscountPercentage() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercentage"), filter.getMinDiscountPercentage()));
            }

            // 5. Filter by Price Range (minPrice)
            if (filter.getMinPrice() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerQuantity"), filter.getMinPrice()));
            }

            // 6. Filter by Price Range (maxPrice)
            if (filter.getMaxPrice() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerQuantity"), filter.getMaxPrice()));
            }

            // 7. Filer by SearchBy (name, description, etc.) - assuming 'name' and 'description' fields
            Predicate searchPredicate = buildSearchByPredicate(root, criteriaBuilder, filter.getSearchBy(), "name", "description");
            if (searchPredicate != null) predicates.add(searchPredicate);

            // 8. Filter by Tags
            Predicate tagsPredicate = buildTagsPredicate(root, criteriaBuilder, filter.getTags());
            if (tagsPredicate != null) predicates.add(tagsPredicate);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
