package org.orphancare.dashboard.specification;

import org.orphancare.dashboard.entity.Guardian;
import org.orphancare.dashboard.entity.GuardianType;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuardianSpecification {

    public static Specification<Guardian> searchGuardians(String fullName, String email, UUID guardianTypeId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fullName != null && !fullName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }

            if (guardianTypeId != null) {
                Join<Guardian, GuardianType> guardianTypeJoin = root.join("guardianType");
                predicates.add(criteriaBuilder.equal(guardianTypeJoin.get("id"), guardianTypeId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}