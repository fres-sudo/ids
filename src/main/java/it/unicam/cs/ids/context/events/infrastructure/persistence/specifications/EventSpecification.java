package it.unicam.cs.ids.context.events.infrastructure.persistence.specifications;

import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventFilter;
import it.unicam.cs.ids.shared.infrastructure.persistence.AbstractSpecification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class EventSpecification extends AbstractSpecification {

    public static Specification<Event> withFilter(EventFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filter by Organizer ID
            if (filter.getOrganizerId() != null && filter.getOrganizerId() >= 0) {
                predicates.add(criteriaBuilder.equal(root.get("organizer").get("id"), filter.getOrganizerId()));
            }

            // 2. Filter by Status
            if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
                predicates.add(root.get("status").in(filter.getStatuses()));
            }

            // 3. Filter by Start Date Range
            if (filter.getStartDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), filter.getStartDateFrom()));
            }
            if (filter.getStartDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), filter.getStartDateTo()));
            }

            // 4. Filter by End Date Range
            if (filter.getEndDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), filter.getEndDateFrom()));
            }
            if (filter.getEndDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), filter.getEndDateTo()));
            }

            // 5. Filter by Public/Private
            if (filter.getIsPublic() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isPublic"), filter.getIsPublic()));
            }

            // 6. Filter by SearchBy (name, description, etc.)
            Predicate searchPredicate = buildSearchByPredicate(root, criteriaBuilder, filter.getSearchBy(), "name", "description");
            if (searchPredicate != null) predicates.add(searchPredicate);

            // 7. Filter by Tags
            Predicate tagsPredicate = buildTagsPredicate(root, criteriaBuilder, filter.getTags());
            if (tagsPredicate != null) predicates.add(tagsPredicate);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
