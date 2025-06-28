package it.unicam.cs.ids.services.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import it.unicam.cs.ids.dtos.filters.CompanyFilter;
import it.unicam.cs.ids.entities.Company;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class CompanySpecification {
    public static Specification<Company> withFilter(CompanyFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 1. Filter by Company Minimum Size
            if (filter.getMinCompanySize() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("company").get("employees").as(Integer.class), filter.getMinCompanySize()));
            }

            // 2. Filter by Company Maximum Size
            if (filter.getMaxCompanySize() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("company").get("employees").as(Integer.class), filter.getMaxCompanySize()));
            }

            // 3. Filter by SearchBy (name, description, etc.) - assuming 'name' and 'description' fields
            if (filter.getSearchBy() != null && !filter.getSearchBy().isEmpty()) {
                String searchTerm = "%" + filter.getSearchBy().toLowerCase() + "%";
                Predicate nameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchTerm);
                Predicate descriptionLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchTerm);
                predicates.add(criteriaBuilder.or(nameLike, descriptionLike));
            }

            // 4. Filter by Tags
            if (filter.getTags() != null && !filter.getTags().isEmpty()) {
                List<Predicate> tagPredicates = new ArrayList<>();
                for (String tag : filter.getTags()) {
                    Join<Company, String> tagsJoin = root.join("tags");
                    tagPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(tagsJoin), "%" + tag.toLowerCase() + "%"));
                }
                predicates.add(criteriaBuilder.or(tagPredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
