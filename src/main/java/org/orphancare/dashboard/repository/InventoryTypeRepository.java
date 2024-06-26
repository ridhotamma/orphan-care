package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.InventoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryTypeRepository extends JpaRepository<InventoryType, UUID> {
}
