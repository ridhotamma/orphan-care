package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Page<Inventory> findByNameContainingIgnoreCase(String name, Pageable pageable);
    @Query(value = "SELECT it.name as inventory_type_name, COUNT(i.id) as count " +
            "FROM inventories i " +
            "JOIN inventory_types it ON i.inventory_type_id = it.id " +
            "GROUP BY it.name",
            nativeQuery = true)
    List<Map<String, Object>> getInventoryCountByType();
}
