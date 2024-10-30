package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, UUID> {
    Optional<Settings> findFirstByOrderByCreatedAtAsc();
}