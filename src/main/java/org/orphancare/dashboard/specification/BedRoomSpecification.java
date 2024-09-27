package org.orphancare.dashboard.specification;

import org.orphancare.dashboard.entity.BedRoom;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class BedRoomSpecification {

    public static Specification<BedRoom> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<BedRoom> bedRoomTypeIdEquals(UUID bedRoomTypeId) {
        return (root, query, cb) ->
                bedRoomTypeId == null ? null : cb.equal(root.get("bedRoomType").get("id"), bedRoomTypeId);
    }
}
