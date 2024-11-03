package org.orphancare.dashboard.specification;

import jakarta.persistence.criteria.Predicate;
import org.orphancare.dashboard.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EventSpecification {
    public static Specification<Event> searchEvents(
            String search,
            Event.EventStatus status,
            LocalDate startDateFrom,
            LocalDate startDateTo,
            LocalDate endDateFrom,
            LocalDate endDateTo
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search predicate
            if (search != null && !search.isEmpty()) {
                String lowercaseSearch = "%" + search.toLowerCase() + "%";
                Predicate searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowercaseSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("organizer")), lowercaseSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("organizerPhoneNumber")), lowercaseSearch),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("place")), lowercaseSearch)
                );
                predicates.add(searchPredicate);
            }

            // Status predicate
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Start date range predicates
            if (startDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDate"), startDateFrom
                ));
            }
            if (startDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("startDate"), startDateTo
                ));
            }

            // End date range predicates
            if (endDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("endDate"), endDateFrom
                ));
            }
            if (endDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDate"), endDateTo
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
