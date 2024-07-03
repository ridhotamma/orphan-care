package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Page<Inventory> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
