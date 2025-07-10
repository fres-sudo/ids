package it.unicam.cs.ids.services.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import it.unicam.cs.ids.dtos.filters.CompanyFilter;
import it.unicam.cs.ids.entities.Company;
import jakarta.persistence.criteria.Predicate;

public final class CompanySpecification extends AbstractSpecification {
    public static Specification<Company> withFilter(CompanyFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 3. Filter by SearchBy (name, description, etc.) - assuming 'name' and 'description' fields
            Predicate searchPredicate = buildSearchByPredicate(root, criteriaBuilder, filter.getSearchBy(), "name", "description");
            if (searchPredicate != null) predicates.add(searchPredicate);

            // 4. Filter by Tags
            Predicate tagsPredicate = buildTagsPredicate(root, criteriaBuilder, filter.getTags());
            if (tagsPredicate != null) predicates.add(tagsPredicate);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
