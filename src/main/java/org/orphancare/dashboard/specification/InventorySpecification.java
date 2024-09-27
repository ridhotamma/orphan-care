package org.orphancare.dashboard.specification;

import org.springframework.data.jpa.domain.Specification;
import org.orphancare.dashboard.entity.Inventory;

import java.util.UUID;

public class InventorySpecification {

    public static Specification<Inventory> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Inventory> inventoryTypeIdEquals(UUID inventoryTypeId) {
        return (root, query, cb) ->
                inventoryTypeId == null ? null : cb.equal(root.get("inventoryType").get("id"), inventoryTypeId);
    }
}
