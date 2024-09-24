package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.BedRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BedRoomTypeRepository extends JpaRepository<BedRoomType, UUID> {
}
