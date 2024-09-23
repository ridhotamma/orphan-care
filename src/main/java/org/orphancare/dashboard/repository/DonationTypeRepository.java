package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DonationTypeRepository extends JpaRepository<DonationType, UUID> {
}