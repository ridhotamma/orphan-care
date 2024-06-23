package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.GuardianType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuardianTypeRepository extends JpaRepository<GuardianType, UUID> {
}
