package it.unicam.cs.ids.services.specifications;

import it.unicam.cs.ids.dtos.filters.ProductFilter;
import it.unicam.cs.ids.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withFilter(ProductFilter filter) {
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

            // 4. Filter by Producer ID
            if (filter.getProducerId() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("producer").get("id"), filter.getProducerId()));
            }

            // 5. Filter by Price Range (minPrice)
            if (filter.getMinPrice() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerQuantity"), filter.getMinPrice()));
            }

            // 6. Filter by Price Range (maxPrice)
            if (filter.getMaxPrice() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerQuantity"), filter.getMaxPrice()));
            }

            // 7. Filter by SearchBy (name, description, etc.) - assuming 'name' and 'description' fields
            if (filter.getSearchBy() != null && !filter.getSearchBy().isEmpty()) {
                String searchTerm = "%" + filter.getSearchBy().toLowerCase() + "%";
                Predicate nameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchTerm);
                Predicate descriptionLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchTerm);
                predicates.add(criteriaBuilder.or(nameLike, descriptionLike));
            }

            // 8. Filter by Tags
            if (filter.getTags() != null && !filter.getTags().isEmpty()) {
                List<Predicate> tagPredicates = new ArrayList<>();
                for (String tag : filter.getTags()) {
                    Join<Product, String> tagsJoin = root.join("tags");
                    tagPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(tagsJoin), "%" + tag.toLowerCase() + "%"));
                }
                predicates.add(criteriaBuilder.or(tagPredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}