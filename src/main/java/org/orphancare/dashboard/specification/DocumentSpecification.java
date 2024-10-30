package org.orphancare.dashboard.specification;

import org.orphancare.dashboard.entity.Document;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class DocumentSpecification {
    public static Specification<Document> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Document> documentTypeIdEquals(UUID documentTypeId) {
        return (root, query, criteriaBuilder) -> {
            if (documentTypeId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("documentType").get("id"), documentTypeId);
        };
    }

    public static Specification<Document> ownerIdEquals(UUID ownerId) {
        return (root, query, criteriaBuilder) -> {
            if (ownerId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
        };
    }
}
