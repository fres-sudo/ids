package it.unicam.cs.ids.context.company.infrastructure.persistence.specifications;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.ids.shared.infrastructure.persistence.AbstractSpecification;
import org.springframework.data.jpa.domain.Specification;

import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyFilter;
import it.unicam.cs.ids.context.company.domain.models.Company;
import jakarta.persistence.criteria.Predicate;

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
