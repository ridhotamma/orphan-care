package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DonationRepository extends JpaRepository<Donation, UUID> {
    Page<Donation> findByNameContainingIgnoreCase(String name, Pageable pageable);
}