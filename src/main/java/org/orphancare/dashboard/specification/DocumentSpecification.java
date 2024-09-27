package org.orphancare.dashboard.specification;

import org.orphancare.dashboard.entity.Document;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class DocumentSpecification {

    public static Specification<Document> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Document> documentTypeIdEquals(UUID documentTypeId) {
        return (root, query, cb) ->
                documentTypeId == null ? null : cb.equal(root.get("documentType").get("id"), documentTypeId);
    }

    public static Specification<Document> ownerIdEquals(UUID ownerId) {
        return (root, query, cb) ->
                ownerId == null ? null : cb.equal(root.get("owner").get("id"), ownerId);
    }
}
