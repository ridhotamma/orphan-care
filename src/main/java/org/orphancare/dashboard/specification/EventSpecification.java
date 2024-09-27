package org.orphancare.dashboard.specification;

import jakarta.persistence.criteria.Predicate;
import org.orphancare.dashboard.entity.Event;
import org.springframework.data.jpa.domain.Specification;


public class EventSpecification {

    public static Specification<Event> searchEvents(String search, Event.EventStatus status) {
        return (root, query, criteriaBuilder) -> {
            Predicate searchPredicate = criteriaBuilder.conjunction();
            if (search != null && !search.isEmpty()) {
                String lowercaseSearch = "%" + search.toLowerCase() + "%";
                searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowercaseSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("organizer")), lowercaseSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("organizerPhoneNumber")), lowercaseSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("place")), lowercaseSearch)
                );
            }

            Predicate statusPredicate = criteriaBuilder.conjunction();
            if (status != null) {
                statusPredicate = criteriaBuilder.equal(root.get("status"), status);
            }

            return criteriaBuilder.and(searchPredicate, statusPredicate);
        };
    }
}