package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.BedRoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BedRoomTypeRepository extends JpaRepository<BedRoomType, UUID> {
}
