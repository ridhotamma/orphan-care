package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.BedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface BedRoomRepository extends JpaRepository<BedRoom, UUID>, JpaSpecificationExecutor<BedRoom> {
    @Query(value = "SELECT bt.name as bedroom_type_name, COUNT(b.id) as count " +
            "FROM bedrooms b " +
            "JOIN bedroom_types bt ON b.bedroom_type_id = bt.id " +
            "GROUP BY bt.name",
            nativeQuery = true)
    List<Map<String, Object>> getBedroomCountByType();
}
