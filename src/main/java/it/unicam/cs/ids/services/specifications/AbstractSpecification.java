package it.unicam.cs.ids.services.specifications;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpecification {
    /**
     * Builds a predicate for the search fields.
     * @param root the root of the query
     * @param criteriaBuilder the criteria builder
     * @param searchBy the search by field
     * @param fields the fields to search by
     * @return the predicate
     */
    protected static <T> Predicate buildSearchByPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String searchBy, String... fields) {
        if (searchBy == null || searchBy.isEmpty()) return null;
        
        String searchTerm = "%" + searchBy.toLowerCase() + "%";
        
        List<Predicate> predicates = new ArrayList<>();
        for (String field : fields)
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)),searchTerm));
        
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Builds a predicate for the tags.
     * @param root the root of the query
     * @param criteriaBuilder the criteria builder
     * @param tags the tags to search by
     * @return the predicate
     */
    protected static <T> Predicate buildTagsPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, List<String> tags) {
        if (tags == null || tags.isEmpty()) return null;
        
        List<Predicate> tagPredicates = new ArrayList<>();
        for (String tag : tags) {
            Join<T, String> tagsJoin = root.join("tags");
            tagPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(tagsJoin),"%" + tag.toLowerCase() + "%"));
        }
        
        return criteriaBuilder.or(tagPredicates.toArray(new Predicate[0]));
    }
}
