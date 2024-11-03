package org.orphancare.dashboard.specification;

import org.orphancare.dashboard.entity.Donation;
import org.orphancare.dashboard.entity.DonationType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DonationSpecification {
    public static Specification<Donation> withSearchCriteria(
            String name,
            LocalDate startDate,
            LocalDate endDate,
            UUID donationTypeId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                String searchLower = "%" + name.toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), searchLower));
            }

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("receivedDate"), startDate, endDate));
            }

            if (donationTypeId != null) {
                Join<Donation, DonationType> donationTypeJoin = root.join("donationType");
                predicates.add(criteriaBuilder.equal(donationTypeJoin.get("id"), donationTypeId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
