package it.unicam.cs.ids.shared.infrastructure.specifications;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.ids.shared.infrastructure.persistence.AbstractSpecification;
import org.springframework.data.jpa.domain.Specification;

import it.unicam.cs.ids.shared.infrastructure.api.filters.CompanyFilter;
import it.unicam.cs.ids.model.Company;
import jakarta.persistence.criteria.Predicate;

/**
 * Specification for filtering companies based on various criteria.
 */
public final class CompanySpecification extends AbstractSpecification {
    public static Specification<Company> withFilter(CompanyFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 3. Filter by SearchBy (name, description, etc.) - assuming 'name' and 'description' fields
            Predicate searchPredicate = buildSearchByPredicate(root, criteriaBuilder, filter.getSearchBy(), "name", "description");
            if (searchPredicate != null) predicates.add(searchPredicate);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
