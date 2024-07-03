package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.BedRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BedRoomRepository extends JpaRepository<BedRoom, UUID> {
    Page<BedRoom> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
