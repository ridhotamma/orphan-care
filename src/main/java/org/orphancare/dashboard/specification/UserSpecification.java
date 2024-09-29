package org.orphancare.dashboard.specification;

import org.orphancare.dashboard.entity.*;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserSpecification {

    public static Specification<User> withSearchCriteriaAndRoles(
            String search, Gender gender, List<RoleType> roles, Boolean isAlumni, Boolean isCareTaker, Boolean active, UUID bedRoomId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<User, Profile> profileJoin = root.join("profile", JoinType.LEFT);

            if (search != null && !search.isEmpty()) {
                String searchLower = "%" + search.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), searchLower),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchLower),
                        criteriaBuilder.like(criteriaBuilder.lower(profileJoin.get("fullName")), searchLower)
                ));
            }

            if (gender != null) {
                predicates.add(criteriaBuilder.equal(profileJoin.get("gender"), gender));
            }

            if (roles != null && !roles.isEmpty()) {
                predicates.add(root.join("roles").in(roles));
            }

            if (isAlumni != null) {
                predicates.add(criteriaBuilder.equal(profileJoin.get("isAlumni"), isAlumni));
            }

            if (isCareTaker != null) {
                predicates.add(criteriaBuilder.equal(profileJoin.get("isCareTaker"), isCareTaker));
            }

            if (active != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), active));
            }

            if (bedRoomId != null) {
                Join<Profile, BedRoom> bedRoomJoin = profileJoin.join("bedRoom", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(bedRoomJoin.get("id"), bedRoomId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
